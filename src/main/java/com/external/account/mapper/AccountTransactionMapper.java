package com.external.account.mapper;

import com.external.account.domain.AccountTransaction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface AccountTransactionMapper {

    /**
     * 자산 구성 / 스냅샷 계산용
     * - 기간 전체
     * - 페이징 없음
     */
    List<AccountTransaction> findTransactionsForAsset(
            @Param("fintechUseNum") String fintechUseNum
    );

    /**
     * 거래내역 조회용 (UI)
     * - 기간 필터
     * - 페이징 고려 가능
     */
    List<AccountTransaction> findTransactionHistory(
            @Param("fintechUseNum") String fintechUseNum,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate,
            @Param("cursor") LocalDateTime cursor
    );
}
