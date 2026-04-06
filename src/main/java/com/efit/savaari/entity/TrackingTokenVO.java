package com.efit.savaari.entity;

import java.time.LocalDateTime;

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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trackingtokengen")
	@SequenceGenerator(name = "trackingtokengen", sequenceName = "trackingtokenseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "trackingtokenid")
	private Long id;

	private String username;

	private String password;

	@Column(length = 2000)
	private String token;

	@Column(name = "refresh_token", length = 2000)
	private String refreshToken;

	@Column(name = "token_expiry")
	private LocalDateTime tokenExpiry;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
