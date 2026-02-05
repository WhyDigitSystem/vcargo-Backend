package com.efit.savaari.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsentResponse {
	
	private String consent;
    private String tel;
    private String operator;
   

}
