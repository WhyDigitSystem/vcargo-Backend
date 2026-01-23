package com.efit.savaari.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tvehicledocuments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TvehicleDocumentsVO {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tvehicledocumentsgen")
    @SequenceGenerator(name = "tvehicledocumentsgen", sequenceName = "tvehicledocumentsseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "tvehicledocumentsid")
    private Long id;

    @Column(name = "documenttype", length = 50)
    private String documentType;   // RC, INSURANCE, FC, PERMIT, PUC, OTHER

    @Column(name = "filename", length = 255)
    private String fileName;

    @Column(name = "filepath", length = 500)
    private String filePath;

    @Column(name = "filetype", length = 50)
    private String fileType;       // PDF, JPG, PNG

    @Column(name = "filesize")
    private Long fileSize;

    @Column(name = "uploadedon")
    private LocalDateTime uploadedOn;
    
	@ManyToOne
	@JoinColumn(name = "tvehicleid")
	@JsonBackReference
	private TvehicleVO tvehicle;
}
