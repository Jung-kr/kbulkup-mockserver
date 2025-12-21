package com.external.account.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum BankCode {

    KB("097", "국민은행"),
    SHINHAN("088", "신한은행"),
    IBK("003", "기업은행"),
    NH("011", "농협은행"),
    WOORI("020", "우리은행"),
    HANA("081", "하나은행"),
    BUSAN("032", "부산은행"),
    GWANGJU("034", "광주은행"),
    JEJU("035", "제주은행"),
    JEONBUK("037", "전북은행"),
    KYONGNAM("039", "경남은행"),
    MG("045", "새마을금고"),
    SUHYUP("007", "수협은행"),
    POST("071", "우체국"),
    KAKAO("090", "카카오뱅크"),
    KBANK("089", "케이뱅크"),
    TOSS("092", "토스뱅크");

    private final String code;
    private final String name;

    /**
     * 은행 코드로 BankCode 찾기
     */
    public static BankCode fromCode(String code) {
        return Arrays.stream(values())
                .filter(bank -> bank.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 은행 코드입니다: " + code));
    }

    /**
     * 은행코드로 은행명 찾기
     */
    public static String getNameByCode(String code) {
        return fromCode(code).getName();
    }
}
