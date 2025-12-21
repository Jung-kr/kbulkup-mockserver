package com.external.oauth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ObUser {
    private String userSeqNo;
    private String userName;
    private LocalDateTime createdAt;
}
