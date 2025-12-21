package com.external.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRegisterResponse {

    private String fintechUseNum;
    private String bankName;
    private String accountNumMasked;
}
