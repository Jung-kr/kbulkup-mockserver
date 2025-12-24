package com.external.account.dto;

import com.external.account.domain.AccountTransaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionListResponse {

    private List<AccountTransaction> transactions;
    private LocalDateTime nextCursor;

    public TransactionListResponse(List<AccountTransaction> transactions) {
        this.transactions = transactions;
        this.nextCursor = null;
    }
}
