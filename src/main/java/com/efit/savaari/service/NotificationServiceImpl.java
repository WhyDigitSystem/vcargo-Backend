package com.efit.savaari.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efit.savaari.entity.MaintenanceVO;
import com.efit.savaari.entity.NotificationVO;
import com.efit.savaari.entity.TdriverVO;
import com.efit.savaari.entity.TvehicleVO;
import com.efit.savaari.entity.UserVO;
import com.efit.savaari.repo.MaintenanceRepo;
import com.efit.savaari.repo.NotificationRepo;
import com.efit.savaari.repo.TdriverRepo;
import com.efit.savaari.repo.TvehicleRepo;
import com.efit.savaari.repo.UserRepo;

@Service
public class NotificationServiceImpl implements NotificationService {

	public static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class);

	@Autowired
	NotificationRepo notificationRepo;

	@Autowired
	private MaintenanceRepo maintenanceRepo;

	@Autowired
	private TvehicleRepo tVehiclesRepo;

	@Autowired
	private TdriverRepo tDriverRepo;
	
	@Autowired
	UserRepo userRepo;

	@Override
	public void createNotification(Long orgId, String message, String type) {
		NotificationVO n = new NotificationVO();
		n.setOrgid(orgId);
		n.setMessage(message);
		n.setNotificationType(type);
		notificationRepo.save(n);
	}

	@Override
	public List<NotificationVO> getNotifications(Long orgId) {
		return notificationRepo.findByOrgidAndIsReadFalseAndIsDeletedFalse(orgId);
	}

	@Override
	@Transactional
	public void markAsRead(Long notificationId) {
		NotificationVO n = notificationRepo.findById(notificationId).orElseThrow();
		n.setRead(true);
		notificationRepo.save(n);
	}

	@Override
	@Transactional
	public void deleteNotification(Long notificationId) {
		NotificationVO n = notificationRepo.findById(notificationId).orElseThrow();
		n.setDeleted(true);
		notificationRepo.save(n);
	}

	@Override
	@Transactional
	public void clearAll(Long orgId) {
		List<NotificationVO> list = notificationRepo.findByOrgid(orgId);
		list.forEach(n -> n.setDeleted(true));
		notificationRepo.saveAll(list);
	}

	// escalation notification

	@Override
	@Transactional
	public void generateEscalationNotifications() {

		// MUST loop companies — dashboard queries filter by orgId = CompanyVO.id.

		List<UserVO> users = userRepo.findAll();

		for (UserVO user : users) {
			generateNotifications(user.getId());
		}
	}

	private void generateNotifications(Long orgId) {

		maintenanceNotification(orgId);
		insuranceNotification(orgId);
		fitnessNotification(orgId);
		pucNotification(orgId);
		driverLicenseNotification(orgId);
	}

	private void maintenanceNotification(Long orgId) {

		List<MaintenanceVO> list = maintenanceRepo.getMaintenanceDashboard(orgId);

		for (MaintenanceVO maintenance : list) {

			if (maintenance.getScheduledDate() == null) {
				continue;
			}

			String vehicleNumber = maintenance.getVehicle() != null
					? maintenance.getVehicle().getVehicleNumber()
					: "";

			String mechanic = maintenance.getMechanic() != null
					? maintenance.getMechanic()
					: "";

			Long userId = (maintenance.getVehicle() != null && maintenance.getVehicle().getUser() != null)
					? maintenance.getVehicle().getUser().getId()
					: null;

			saveNotification(
					orgId,
					"MAINTENANCE",
					maintenance.getId(),
					vehicleNumber,
					mechanic,
					maintenance.getScheduledDate(),
					userId);
		}
	}

	private void insuranceNotification(Long orgId) {
		List<TvehicleVO> list = tVehiclesRepo.getInsuranceDashboard(orgId);
		for (TvehicleVO vehicle : list) {
			if (vehicle.getInsuranceExpiry() == null) continue;

			String vehicleNumber = vehicle.getVehicleNumber() != null ? vehicle.getVehicleNumber() : "";
			String driver = vehicle.getDriver() != null ? vehicle.getDriver() : "";
			Long userId = vehicle.getUser() != null ? vehicle.getUser().getId() : null;

			saveNotification(orgId, "INSURANCE", vehicle.getId(), vehicleNumber, driver,
					vehicle.getInsuranceExpiry(), userId);
		}
	}

	private void fitnessNotification(Long orgId) {

		List<TvehicleVO> list = tVehiclesRepo.getFitnessDashboard(orgId);

		for (TvehicleVO vehicle : list) {

			if (vehicle.getFitnessExpiry() == null) {
				continue;
			}

			String vehicleNumber = vehicle.getVehicleNumber() != null
					? vehicle.getVehicleNumber()
					: "";

			String driver = vehicle.getDriver() != null
					? vehicle.getDriver()
					: "";

			Long userId = vehicle.getUser() != null ? vehicle.getUser().getId() : null;

			saveNotification(
					orgId,
					"FITNESS",
					vehicle.getId(),
					vehicleNumber,
					driver,
					vehicle.getFitnessExpiry(),
					userId);
		}
	}

	private void pucNotification(Long orgId) {

		List<TvehicleVO> list = tVehiclesRepo.getPucDashboard(orgId);

		for (TvehicleVO vehicle : list) {

			if (vehicle.getPucExpiry() == null) {
				continue;
			}

			String vehicleNumber = vehicle.getVehicleNumber() != null
					? vehicle.getVehicleNumber()
					: "";

			String driver = vehicle.getDriver() != null
					? vehicle.getDriver()
					: "";

			Long userId = vehicle.getUser() != null ? vehicle.getUser().getId() : null;

			saveNotification(
					orgId,
					"PUC",
					vehicle.getId(),
					vehicleNumber,
					driver,
					vehicle.getPucExpiry(),
					userId);
		}
	}

	private void driverLicenseNotification(Long orgId) {

		List<TdriverVO> list = tDriverRepo.getDriverDashboard(orgId);

		for (TdriverVO driver : list) {

			if (driver.getLicenseExpiry() == null) {
				continue;
			}

			String assignedVehicle = driver.getAssignedVehicle() != null
					? driver.getAssignedVehicle()
					: "";

			String driverName = driver.getName() != null
					? driver.getName()
					: "";

			Long userId = driver.getUser() != null ? driver.getUser().getId() : null;

			saveNotification(
					orgId,
					"LICENSE",
					driver.getId(),
					assignedVehicle,
					driverName,
					driver.getLicenseExpiry(),
					userId);
		}
	}

	private void saveNotification(
			Long orgId,
			String type,
			Long referenceId,
			String vehicleNumber,
			String driverName,
			LocalDate dueDate,
			Long userId) {

		long days = ChronoUnit.DAYS.between(LocalDate.now(), dueDate);

		String stage = null;

		if (days == 30) {

			stage = "MONTH";

		} else if (days == 7) {

			stage = "WEEK";

		} else if (days == 1) {

			stage = "DAY";

		} else if (days < 0) {

			stage = "OVERDUE";

		}

		if (stage == null)
			return;

		boolean exists =
				notificationRepo.existsByNotificationTypeAndReferenceIdAndNotificationStage(
						type,
						referenceId,
						stage);

		if (exists)
			return;

		NotificationVO notification = new NotificationVO();

		notification.setOrgid(orgId);
		notification.setNotificationType(type);
		notification.setReferenceId(referenceId);
		notification.setVehicleNumber(vehicleNumber);
		notification.setDriverName(driverName);
		notification.setNotificationStage(stage);
		notification.setDueDate(dueDate);
		notification.setSeverity(getSeverity(days));
		notification.setTitle(type + " Reminder");
		notification.setMessage(buildMessage(type, vehicleNumber, driverName, days));
		notification.setUserId(userId);

		notificationRepo.save(notification);
	}

	private String getSeverity(long days) {

		if (days < 0)
			return "Critical";

		if (days <= 1)
			return "Critical";

		if (days <= 7)
			return "High";

		if (days <= 30)
			return "Medium";

		return "Low";
	}

	private String buildMessage(
			String type,
			String vehicleNumber,
			String driverName,
			long days) {

		if (days < 0) {
			return type + " has expired for vehicle " + vehicleNumber;
		}

		switch (type) {

			case "LICENSE":
				return "Driver " + driverName +
					   " license expires in " + days + " day(s).";

			case "INSURANCE":
				return "Insurance for vehicle " +
						vehicleNumber +
						" expires in " + days + " day(s).";

			case "FITNESS":
				return "Fitness certificate for vehicle " +
						vehicleNumber +
						" expires in " + days + " day(s).";

			case "PUC":
				return "PUC for vehicle " +
						vehicleNumber +
						" expires in " + days + " day(s).";

			case "MAINTENANCE":
				return "Maintenance due for vehicle " +
						vehicleNumber +
						" in " + days + " day(s).";

			default:
				return type + " reminder.";
		}
	}

}
