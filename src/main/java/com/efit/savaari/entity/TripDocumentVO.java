package com.efit.savaari.entity;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "trip_documents")
@Data
public class TripDocumentVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tripdocgen")
    @SequenceGenerator(
        name = "tripdocgen",
        sequenceName = "tripdocseq",
        initialValue = 1000000001,
        allocationSize = 1
    )
    private Long documentId;

    private String documentType;

    @Lob
    private byte[] document;

    @ManyToOne
    @JoinColumn(name = "tripid")
    private TripVO tripVO;
}
