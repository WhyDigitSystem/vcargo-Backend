package com.efit.savaari.responseDTO;

import lombok.Data;

@Data
public class TraqoErrorResponse {

	private String error;

	public TraqoErrorResponse() {
	}

	public TraqoErrorResponse(String error) {
		this.error = error;
	}

}
