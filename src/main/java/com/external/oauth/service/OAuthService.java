package com.external.oauth.service;

import com.external.oauth.domain.ObUser;
import com.external.oauth.dto.TokenResponse;
import com.external.oauth.mapper.ObUserMapper;
import com.external.common.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final JwtUtil jwtUtil;
    private final ObUserMapper obUserMapper;

    /**
     * 사용자 등록 및 Authorization Code 발급
     */
    @Transactional
    public String registerUserAndIssueCode(String username, String clientId) {

        //user_seq_no 생성 (110 + 7자리 랜덤)
        String userSeqNo = "110" + String.format("%07d", (int) (Math.random() * 10000000));

        //ob_users에 저장
        ObUser obUser = new ObUser(userSeqNo, username, LocalDateTime.now());
        obUserMapper.insertUser(obUser);

        // Authorization Code 생성
        String authCode = UUID.randomUUID().toString().replace("-", "").substring(0, 13);

        //redis에 저장후 10분 후 자동 삭제

        return authCode;
    }

    /**
     * Authorization Code로 JWT 토큰 발급
     */
    public TokenResponse issueToken(String authCode) {
        //redis에서 코드 검증

        //redis에서 코드 삭제(authCode는 1회용)

        //redis에서 삭제하면서 추출
        String userSeqNo = authCode;

        String accessToken = jwtUtil.generateToken(userSeqNo);
        String refreshToken = jwtUtil.generateToken(userSeqNo);

        return TokenResponse.create(accessToken, refreshToken);
    }

}
