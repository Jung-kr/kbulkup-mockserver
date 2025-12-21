package com.external.oauth.mapper;

import com.external.oauth.domain.ObUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ObUserMapper {

    //사용자 등록
    void insertUser(ObUser obUser);

    //user_seq_no로 사용자 조회
    ObUser getUserBySeqNo(@Param("userSeqNo") String userSeqNo);
}
