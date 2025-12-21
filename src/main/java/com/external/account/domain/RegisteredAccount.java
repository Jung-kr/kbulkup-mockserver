package com.external.account.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredAccount {

    private String fintechUseNum;
    private String userSeqNo;
    private String bankCode;
    private String bankName;
    private String accountNum;
    private String accountHolderName;
    private Long balanceAmt;
    private Long availableAmt;
    private LocalDateTime createdAt;

    public static RegisteredAccount create(String fintechUseNum, String userSeqNo, String bankCode, String bankName,
                                           String accountNum, String accountHolderName, Long balanceAmt, Long availableAmt) {
        return new RegisteredAccount(fintechUseNum, userSeqNo, bankCode, bankName, accountNum, accountHolderName, balanceAmt, availableAmt, LocalDateTime.now());
    }
}
