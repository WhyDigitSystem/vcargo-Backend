package com.efit.savaari.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

//@Entity
//@Table(name = "notification")
//@Data
//public class NotificationVO {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long notificationId;
//
//    private Long orgid;            // To whom notification goes
//    private String message;
//    private boolean isRead = false;
//    private boolean isDeleted = false;
//    private String notificationType;
//
//    private LocalDateTime createdOn = LocalDateTime.now();
//}

@Entity
@Table(name = "notification")
@Data
public class NotificationVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    @Column(name = "orgid", nullable = false)
    private Long orgid;

    @Column(name = "userid")
    private Long userId;

    // MAINTENANCE, INSURANCE, FITNESS, PUC, LICENSE
    @Column(name = "notificationtype", length = 50)
    private String notificationType;

    // Maintenance Id / Vehicle Id / Driver Id
    @Column(name = "referenceid")
    private Long referenceId;

    @Column(name = "vehicle_number", length = 30)
    private String vehicleNumber;

    @Column(name = "drivername", length = 100)
    private String driverName;

    @Column(name = "title", length = 200)
    private String title;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    // MONTH, WEEK, DAY, OVERDUE
    @Column(name = "notificationstage", length = 20)
    private String notificationStage;

    @Column(name = "duedate")
    private LocalDate dueDate;

    // Critical, High, Medium, Low
    @Column(name = "severity", length = 20)
    private String severity;

    @Column(name = "isread")
    private boolean isRead = false;

    @Column(name = "isdeleted")
    private boolean isDeleted = false;

    @Column(name = "createdon")
    private LocalDateTime createdOn = LocalDateTime.now();
}