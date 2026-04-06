package com.efit.savaari.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TokenScheduler {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(TokenScheduler.class);

    @Autowired
    private TrackingTokenService trackingTokenService;

    // run every 5 minutes
    @Scheduled(fixedDelay = 2 * 60 * 1000)
    public void checkTokenExpiryJob() {

        LOGGER.info("Running Token Expiry Check Scheduler");

        try {
            trackingTokenService.autoRefreshIfExpired();
        } catch (Exception e) {
            LOGGER.error("Token auto refresh failed", e);
        }
    }
}
