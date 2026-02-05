package com.efit.savaari.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TraqTripDTO {

    private String trip_id;
    private String trip_uid;
    private String phone_number;
    private String truck_number;
    private String start_time;
    private Long tel;
    private String invoice;
    private String lr_number;
    private String consent_status;
    private boolean is_completed;
    private String distance_travel;
    private String total_distance;
    private String share_url;
    private String trip_status;
    private Double speed;

    private LocationDTO origin;
    private LocationDTO destination;
    private LastLocationDTO last_loc;

    // getters & setters
}

