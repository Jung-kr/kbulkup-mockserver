package com.external.portfolio.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class Transaction {
    private Long transactionId;
    private Long userId;
    private TransactionType transactionType;
    private Long amount;
    private TransactionCategory transactionCategory;
    private LocalDateTime tranDate;

    public static Transaction createTransaction(
            Long transactionId,
            Long userId,
            Long amount,
            TransactionCategory transactionCategory,
            LocalDateTime tranDate
    ) {
        return Transaction.builder()
                .transactionId(transactionId)
                .userId(userId)
                .amount(amount)
                .transactionCategory(transactionCategory)
                .tranDate(tranDate)
                .build();
    }
}
