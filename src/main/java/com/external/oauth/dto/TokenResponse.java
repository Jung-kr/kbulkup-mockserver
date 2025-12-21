package com.external.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {

    private String accessToken;
    private String refreshToken;

    public static TokenResponse create(String accessToken, String refreshToken) {
        return new TokenResponse(accessToken, refreshToken);
    }
}
