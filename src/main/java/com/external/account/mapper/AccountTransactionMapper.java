package com.external.account.mapper;

import com.external.account.domain.AccountTransaction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface AccountTransactionMapper {

    List<AccountTransaction> findTransactionList(@Param("fintechUseNum") String fintechUseNum,
                                                 @Param("fromDate") LocalDate fromDate,
                                                 @Param("toDate") LocalDate toDate);
}
