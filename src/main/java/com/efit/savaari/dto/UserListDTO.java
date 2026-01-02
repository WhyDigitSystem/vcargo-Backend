package com.efit.savaari.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserListDTO {

    private Long userId;
    private String userName;
    private String email;
    private String mobileNo;
    private String password;
    private String type;
    private Long orgId;
    private String organizationName;
    private String branch;
    private String branchCode;
    private Boolean active;
    private UserStatus status;
    private String createdBy;
    private String modifiedBy;
    private String createdOn;
    private String modifiedOn;
}
