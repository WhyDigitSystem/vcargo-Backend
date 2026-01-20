package com.efit.savaari.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efit.savaari.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trackingtoken")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackingTokenVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "companyaddressgen")
	@SequenceGenerator(name = "companyaddressgen", sequenceName = "companyaddressseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "companyaddressid")
	private Long id;
	
	@Column(length = 2000)
    private String token;

    @Column(name = "refresh_token", length = 2000)
    private String refreshToken;
    
    
    
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
