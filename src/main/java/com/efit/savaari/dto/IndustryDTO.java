package com.efit.savaari.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndustryDTO {
	private Long id;
	@NotBlank(message = "Industry code is required.")
	@Size(min = 3, max = 20, message = "Industry code must be 3–20 characters.")
	private String industryCode;

	@NotBlank(message = "Industry name is required.")
	@Size(min = 3, max = 100, message = "Industry name must be 3–100 characters.")
	private String industryName;

	@NotBlank(message = "Phone number is required.")
	@Pattern(regexp = "^[6-9]\\d{9}$", message = "Phone number must be a valid 10-digit mobile number.")
	private String phoneNumber;

	@NotBlank(message = "Email is required.")
	@Email(message = "Invalid email format.")
	private String email;

	@NotBlank(message = "State is required.")
	private String state;

	@NotBlank(message = "GST number is required.")
	@Pattern(regexp = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$", message = "Invalid GST number format.")
	private String gstNumber;

	@NotBlank(message = "PAN number is required.")
	@Pattern(regexp = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$", message = "Invalid PAN number format.")
	private String panNumber;

	@NotBlank(message = "Primary address is required.")
	@Size(min = 5, max = 250, message = "Primary address must be 5–250 characters.")
	private String primaryAddress;

	@Size(max = 250, message = "Additional address cannot exceed 250 characters.")
	private String additionalAddress;

	@NotBlank(message = "City is required.")
	@Size(min = 2, max = 100, message = "City must be 2–100 characters.")
	private String city;

	@NotBlank(message = "Industry type is required.")
	@Pattern(regexp = "Transporter|Industry", message = "Industry type must be Transporter or Industry.")
	private String industryType;

	@Min(value = 100000, message = "PIN code must be a valid 6-digit number.")
	@Max(value = 999999, message = "PIN code must be a valid 6-digit number.")
	private int pincode;

	@NotBlank(message = "POC name is required.")
	@Size(min = 3, max = 100, message = "POC name must be 3–100 characters.")
	private String pocName;

	@NotBlank(message = "POC email is required.")
	@Email(message = "Invalid POC email format.")
	private String pocEmail;

	@NotBlank(message = "POC mobile number is required.")
	@Pattern(regexp = "^[6-9]\\d{9}$", message = "POC number must be a valid 10-digit mobile number.")
	private String pocNumber;

	@NotBlank(message = "CreatedBy is required.")
	private String createdBy;

	@NotNull(message = "User ID is required.")
	private Long userId;

	@NotBlank(message = "User name is required.")
	@Size(min = 4, max = 50, message = "User name must be 4–50 characters.")
	private String userName;

	private String userPassword;

}
