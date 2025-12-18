package com.external.portfolio.mapper;

import com.external.portfolio.domain.Composition;
import com.external.portfolio.domain.Snapshot;
import com.external.portfolio.domain.Transaction;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface UserAssetMapper {
     void assignSnapshotPools(@Param("fintechUseNum") String fintechUseNum,
                             @Param("snapshots") List<Snapshot> snapshots);

    void assignTransactionPools(@Param("userId") Long userId, @Param("transactions") List<Transaction> transactions);

    Long findPortfolioIdByFintechUseNum(@Param("fintechUseNum") String fintechUseNum);

    List<Snapshot> findSnapshotsByUserId(@Param("userId") Long userId);

    List<Transaction> findTransactionByUserId(@Param("userId") Long userId,
                                                     @Param("start") LocalDate start,
                                                     @Param("end") LocalDate end,
                                                     @Param("cursor")LocalDateTime cursor);

    Composition findCompositionByUserId(@Param("userId") Long userId);
}
