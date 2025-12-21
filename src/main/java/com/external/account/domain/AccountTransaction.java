package com.external.account.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransaction {

    private Long tranNo;
    private String fintechUseNum;
    private LocalDateTime tranDate;
    private String inoutType;
    private Long tranAmt;
    private Long afterBalanceAmt;
    private String printedContent;
    private LocalDateTime createdAt;
}
