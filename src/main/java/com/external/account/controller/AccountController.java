package com.external.account.controller;

import com.external.account.dto.AccountRegisterRequest;
import com.external.account.dto.AccountRegisterResponse;
import com.external.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/external/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    /**
     * Step 12: 계좌 등록
     */
    @PostMapping
    public AccountRegisterResponse registerAccount(
            @RequestHeader("Authorization") String authorization,
            @RequestBody AccountRegisterRequest request
    ) {
        return accountService.registerAccount(authorization, request.getBankCode(), request.getAccountNum(), request.getAccountHolderName());
    }
}
