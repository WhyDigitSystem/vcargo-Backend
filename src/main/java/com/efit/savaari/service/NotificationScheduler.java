package com.efit.savaari.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationScheduler {

    @Autowired
    private NotificationService notificationService;

    @Scheduled(cron = "0 0 23 * * ?")
    public void notificationScheduler() {

    	System.out.println("Schedular start");
    	System.out.println("Schedular start");
    	System.out.println("Schedular start");
    	System.out.println("Schedular start");
    	System.out.println("Schedular start");

        notificationService.generateEscalationNotifications();

    }
}