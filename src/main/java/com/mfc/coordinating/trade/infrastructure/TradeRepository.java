package com.mfc.coordinating.trade.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfc.coordinating.trade.domain.Trade;

public interface TradeRepository extends JpaRepository<Trade, Long> {
}
