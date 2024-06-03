package com.mfc.coordinating.trade.application;

import com.mfc.coordinating.trade.dto.request.TradeRequest;
import com.mfc.coordinating.trade.dto.request.TradeUpdateRequest;
import com.mfc.coordinating.trade.dto.response.TradeResponse;

public interface TradeService {
	TradeResponse createTrade(TradeRequest tradeRequest, String partnerUuid);
	TradeResponse getTradeById(Long id, String uuid);
	void updateTrade(Long id, TradeUpdateRequest updatedTradeRequest, String partnerUuid);
	void deleteTrade(Long id, String partnerUuid);
	void updateTradeStatus(Long id, String userUuid);
}
