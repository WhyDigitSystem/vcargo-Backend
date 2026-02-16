package com.efit.savaari.repo;

public interface DriverStatusCountProjection {
    Long getActiveCount();
    Long getInactiveCount();
    Long getLeaveCount();
}
