package com.external.account.controller;

import com.external.account.domain.AccountTransaction;
import com.external.account.dto.TransactionListResponse;
import com.external.account.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/external/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    /**
     * 거래 내역 최근 3개월 조회 (UI 표시용)
     */
    @GetMapping("/history")
    public TransactionListResponse getTransactionHistory(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("fintech_use_num") String fintechUseNum,
            @RequestParam(value = "cursor", required = false) LocalDateTime cursor
            ) {
        return transactionService.getTransactionHistory(authorization, fintechUseNum, cursor);
    }

    /**
     * 자산 구성, 스냅샷 계산용 전체 거래 내역 조회
     */
    @GetMapping("/asset")
    public List<AccountTransaction> getTransactionsForAsset(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("fintech_use_num") String fintechUseNum
    ) {
        return transactionService.getTransactionsForAsset(authorization, fintechUseNum);
    }
}
