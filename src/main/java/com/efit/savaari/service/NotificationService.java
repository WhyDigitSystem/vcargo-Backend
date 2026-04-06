package com.efit.savaari.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.efit.savaari.entity.NotificationVO;

@Service
public interface NotificationService {
	

    List<NotificationVO> getNotifications(Long orgId);

    void markAsRead(Long notificationId);

    void deleteNotification(Long notificationId);

    void clearAll(Long orgId);

	void createNotification(Long orgId, String message, String type);


}
