package com.efit.savaari.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Embedded;

import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
	private Long usersId;
	private String userName;
	private String mobileNo;
	private String email;
	private String type;
	private String organizationName;
	private Long orgId;
	private String branch;
	private String branchCode;
	private boolean active;
	private String createdby;
	private UserStatus status;
	
//	private List<Map<String, Object>> roleVO;

//	
    @Embedded
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
//    private Date accountRemovedDate;
    private String token;
    private String tokenId;
	

    @JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}
    // Setter method to accept List<Map<String, Object>>
   
   
}
