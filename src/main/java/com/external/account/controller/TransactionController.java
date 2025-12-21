package com.external.account.controller;

import com.external.account.dto.TransactionListResponse;
import com.external.account.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/external/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public List<TransactionListResponse> getTransactions(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("fintech_use_num") String fintechUseNum
    ) {
        return transactionService.getTransactions(authorization, fintechUseNum);
    }
}
