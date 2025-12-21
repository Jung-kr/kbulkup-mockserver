package com.external.account.dto;

import lombok.Getter;

@Getter
public class AccountRegisterRequest {

    private String bankCode;
    private String accountNum;
    private String accountHolderName;
}
