package com.external.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionListResponse {

    private LocalDateTime tranDate;
    private String inoutType;
    private Long tranAmt;
    private Long afterBalanceAmt;
    private String printedContent;
}
