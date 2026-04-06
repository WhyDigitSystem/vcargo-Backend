package com.efit.savaari.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efit.savaari.entity.NotificationVO;
import com.efit.savaari.repo.NotificationRepo;

@Service
public class NotificationServiceImpl implements NotificationService {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class);
	
	
	@Autowired
	NotificationRepo notificationRepo;

	@Override
	public void createNotification(Long orgId, String message,String type) {
		NotificationVO n = new NotificationVO();
        n.setOrgid(orgId);
        n.setMessage(message);
        n.setNotificationType(type);
        notificationRepo.save(n);	
	}

	@Override
	public List<NotificationVO> getNotifications(Long orgId) {
		// TODO Auto-generated method stub
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

}
