/*
 * ========================================================================
 * This file is the intellectual property of GSM Outdoors.it
 * may not be copied in whole or in part without the express written
 * permission of GSM Outdoors.
 * ========================================================================
 * Copyrights(c) 2023 GSM Outdoors. All rights reserved.
 * ========================================================================
 */
package com.efit.savaari.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpFormDTO {
	
	private Long id;
	private String userName;
	private String mobileNo;
	private String email;
	private String password;
	private String type;
	private String organizationName;
	private Long orgId;
	private String branch;
	private String branchCode;
	private boolean active;
	private String createdby;
	private UserStatus status;


}
