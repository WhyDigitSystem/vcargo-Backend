package com.efit.savaari.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
public class PayloadDTO {

	@JsonProperty("Data")
    private String Data;

}
