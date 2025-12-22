package com.external.account.service;

import com.external.account.domain.AccountTransaction;
import com.external.account.domain.RegisteredAccount;
import com.external.account.dto.TransactionListResponse;
import com.external.account.mapper.AccountTransactionMapper;
import com.external.account.mapper.RegisteredAccountMapper;
import com.external.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final JwtUtil jwtUtil;
    private final RegisteredAccountMapper accountMapper;
    private final AccountTransactionMapper transactionMapper;

    public List<TransactionListResponse> getTransactionHistory(String accessToken, String fintechUseNum) {
        //access token에서 user_seq_no 추출
        String userSeqNo = jwtUtil.getUserSeqNo(accessToken);

        //계좌 소유권 확인
        RegisteredAccount account = accountMapper.findAccountByUserSeqNoAndFintechUseNum(userSeqNo, fintechUseNum);
        if(account == null){
            throw new IllegalArgumentException("계좌를 찾을 수 없거나 소유권이 없습니다");
        }

        //최근 3개월 거래내역 조회
        LocalDate toDate = LocalDate.now();
        LocalDate fromDate = toDate.minusMonths(3).withDayOfMonth(1);
        List<AccountTransaction> transactions = transactionMapper.findTransactionHistory(fintechUseNum, fromDate, toDate);

        return transactions.stream()
                .map(tx -> new TransactionListResponse(
                        tx.getTranDate(),
                        tx.getInoutType(),
                        tx.getTranAmt(),
                        tx.getAfterBalanceAmt(),
                        tx.getPrintedContent()
                ))
                .toList();
    }

    public List<TransactionListResponse> getTransactionsForAsset(String accessToken, String fintechUseNum) {
        //access token에서 user_seq_no 추출
        String userSeqNo = jwtUtil.getUserSeqNo(accessToken);

        //계좌 소유권 확인
        RegisteredAccount account = accountMapper.findAccountByUserSeqNoAndFintechUseNum(userSeqNo, fintechUseNum);
        if(account == null){
            throw new IllegalArgumentException("계좌를 찾을 수 없거나 소유권이 없습니다");
        }

        //전체 거래내역 조회
        List<AccountTransaction> transactions = transactionMapper.findTransactionsForAsset(fintechUseNum);

        return transactions.stream()
                .map(tx -> new TransactionListResponse(
                        tx.getTranDate(),
                        tx.getInoutType(),
                        tx.getTranAmt(),
                        tx.getAfterBalanceAmt(),
                        tx.getPrintedContent()
                ))
                .toList();
    }
}
