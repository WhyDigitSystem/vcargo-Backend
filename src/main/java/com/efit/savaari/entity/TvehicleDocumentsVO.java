package com.efit.savaari.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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

    @Column(name = "name", length = 50, unique = true)
    private String name;
    
	@Lob
	@Column(name = "documents")
	private byte[] documents;

//	@ManyToOne
//	@JoinColumn(name = "tvehicleid")
//	@JsonBackReference
//	private TvehicleVO tvehicleVO;
}
