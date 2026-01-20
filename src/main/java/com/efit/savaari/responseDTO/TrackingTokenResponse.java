package com.efit.savaari.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackingTokenResponse {

    private String token;
    private String refresh_token;

    // getters & setters
}
