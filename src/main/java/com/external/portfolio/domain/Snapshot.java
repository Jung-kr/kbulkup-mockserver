package com.external.portfolio.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class Snapshot {
    private Long userId;
    private Long balance;
    private LocalDateTime snapshotDate;

    public static Snapshot create(Long userId, Long balance, LocalDateTime snapshotDate) {
        return Snapshot.builder()
                .userId(userId)
                .balance(balance)
                .snapshotDate(snapshotDate)
                .build();
    }
}
