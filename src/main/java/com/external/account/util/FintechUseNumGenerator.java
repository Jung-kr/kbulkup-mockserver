package com.external.account.util;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class FintechUseNumGenerator {

    private static final String CLIENT_ID = "client_001";

    /**
     * 핀테크이용번호 생성
     * SHA256(client_id + user_seq_no + account_num).substring(0, 24)
     */
    public String generateFintechUseNum(String userSeqNo, String accountNum) {
        try {
            String source = CLIENT_ID + userSeqNo + accountNum;

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(source.getBytes(StandardCharsets.UTF_8));

            // byte를 hex String 으로 변환
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            // 앞 24자리만 반환
            return hexString.substring(0, 24);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 알고리즘을 찾을 수 없습니다", e);
        }
    }

    /**
     * 핀테크 이용번호 검증
     */
    public boolean validate(String fintechUseNum, String userSeqNo, String accountNum) {
        return fintechUseNum.equals(generateFintechUseNum(userSeqNo, accountNum));
    }
}
