package com.external.account.mapper;

import com.external.account.domain.RegisteredAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RegisteredAccountMapper {

    //계좌 등록
    void insertAccount(RegisteredAccount account);

    //fintech_use_num 으로 계좌 조회
    RegisteredAccount findAccountByFintechUseNum(@Param("fintechUseNum") String fintechUseNum);

    //user_seq_no와 fintech_use_num 으로 계좌 조회 (소유권 확인)
    RegisteredAccount findAccountByUserSeqNoAndFintechUseNum(
            @Param("userSeqNo") String userSeqNo,
            @Param("fintechUseNum") String fintechUseNum
    );
}
