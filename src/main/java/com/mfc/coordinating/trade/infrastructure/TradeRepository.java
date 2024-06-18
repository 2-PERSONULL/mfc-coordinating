package com.mfc.coordinating.trade.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfc.coordinating.trade.domain.Trade;

public interface TradeRepository extends JpaRepository<Trade, Long> {
	Optional<Trade> findByRequestIdAndPartnerId(String requestId, String partnerId);
	Optional<Trade> findByRequestId(String requestId);
}
