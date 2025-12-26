package com.external.account.service;

import com.external.account.domain.BankCode;
import com.external.account.domain.RegisteredAccount;
import com.external.account.dto.AccountRegisterResponse;
import com.external.account.mapper.RegisteredAccountMapper;
import com.external.account.util.FintechUseNumGenerator;
import com.external.oauth.domain.ObUser;
import com.external.oauth.mapper.ObUserMapper;
import com.external.common.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final JwtUtil jwtUtil;
    private final ObUserMapper obUserMapper;
    private final RegisteredAccountMapper accountMapper;
    private final FintechUseNumGenerator fintechUseNumGenerator;

    /**
     * 계좌 등록
     */
    @Transactional
    public AccountRegisterResponse registerAccount(String accessToken, String bankCode, String accountNum, String accountHolderName) {
        //access token에서 user_seq_no 추출
        String userSeqNo = jwtUtil.getUserSeqNo(accessToken);

        //사용자 존재 확인
        ObUser user = obUserMapper.getUserBySeqNo(userSeqNo);
        if (user == null) {
            throw new IllegalArgumentException("등록되지 않은 사용자입니다");
        }
        //본인 확인 (예금주명 일치 여부)
        if (!accountHolderName.equals(user.getUserName())) {
            throw new IllegalArgumentException("본인 명의의 계좌만 등록할 수 있습니다");
        }

        //fintech_use_num 생성
        String fintechUseNum = fintechUseNumGenerator.generateFintechUseNum(userSeqNo, accountNum);

        //은행명 가져오기
        String bankName = BankCode.getNameByCode(bankCode);

        //계좌 등록 (초기 잔액은 랜덤으로 설정 - Mock용)
        long initialBalance = (long) (Math.random() * 10000000); // 0 ~ 1000만원

        RegisteredAccount account = RegisteredAccount.create(fintechUseNum, userSeqNo, bankCode, bankName, accountNum, accountHolderName, initialBalance, initialBalance);
        accountMapper.insertAccount(account);

        return new AccountRegisterResponse(fintechUseNum, bankName, maskAccountNumber(accountNum));
    }

    /**
     * 계좌번호 마스킹 (123****890 형식)
     */
    private String maskAccountNumber(String accountNum) {
        if (accountNum.length() < 6) {
            return accountNum;
        }

        String prefix = accountNum.substring(0, 3);
        String suffix = accountNum.substring(accountNum.length() - 3);

        return prefix + "****" + suffix;
    }
}
