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
@Table(name = "tdriverdocuments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TdriverDocumentsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tdriverdocsgen")
    @SequenceGenerator(name = "tdriverdocsgen", sequenceName = "tdriverdocsseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "tdriverdocumentsid")
    private Long id;

    @Column(name = "name")
    private String name;
    
	@Lob
	@Column(name = "documents")
	private byte[] documents;

//    @ManyToOne
//    @JoinColumn(name = "tdriverid")
//    @JsonBackReference
//    private TdriverVO tdriverVO;
}
