package com.efit.savaari.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "notification")
@Data
public class NotificationVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    private Long orgid;            // To whom notification goes
    private String message;
    private boolean isRead = false;
    private boolean isDeleted = false;
    private String notificationType;

    private LocalDateTime createdOn = LocalDateTime.now();
}