package com.efit.savaari.entity;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "trip_waypoints")
@Data
public class TripWaypointVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tripwaypointgen")
    @SequenceGenerator(
        name = "tripwaypointgen",
        sequenceName = "tripwaypointseq",
        initialValue = 1000000001,
        allocationSize = 1
    )
    private Long waypointId;

    private String location;
    private Integer sequenceNo;

    @ManyToOne
    @JoinColumn(name = "tripid")
    private TripVO tripVO;
}
