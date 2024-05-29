package com.mfc.coordinating.trade.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mfc.coordinating.common.exception.BaseException;
import com.mfc.coordinating.common.response.BaseResponseStatus;
import com.mfc.coordinating.trade.domain.Trade;
import com.mfc.coordinating.trade.dto.request.TradeRequest;
import com.mfc.coordinating.trade.dto.request.TradeUpdateRequest;
import com.mfc.coordinating.trade.dto.response.TradeResponse;
import com.mfc.coordinating.trade.infrastructure.TradeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TradeServiceImpl implements TradeService {

	private final TradeRepository tradeRepository;

	@Override
	public TradeResponse createTrade(TradeRequest tradeRequest, String partnerUuid) {
		if (!tradeRequest.getPartnerId().equals(partnerUuid)) {
			throw new BaseException(BaseResponseStatus.UNAUTHORIZED_ACCESS);
		}
		Trade confirms = mapToEntity(tradeRequest);
		Trade createdConfirms = tradeRepository.save(confirms);
		return mapToResponse(createdConfirms);
	}

	@Override
	@Transactional(readOnly = true)
	public TradeResponse getTradeById(Long id, String uuid) {
		Trade confirms = tradeRepository.findById(id)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.CONFIRMS_NOT_FOUND));

		if (!confirms.getPartnerId().equals(uuid) && !confirms.getUserId().equals(uuid)) {
			throw new BaseException(BaseResponseStatus.UNAUTHORIZED_ACCESS);
		}

		return mapToResponse(confirms);
	}

	@Override
	public void updateTrade(Long id, TradeUpdateRequest updatedConfirmsRequest, String partnerUuid) {
		Trade confirms = tradeRepository.findById(id)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.CONFIRMS_NOT_FOUND));

		if (!confirms.getPartnerId().equals(partnerUuid)) {
			throw new BaseException(BaseResponseStatus.UNAUTHORIZED_ACCESS);
		}

		confirms.updateTrade(updatedConfirmsRequest.getDueDate(), updatedConfirmsRequest.getTotalPrice(),
			updatedConfirmsRequest.getOptions());
		Trade updatedTrade = tradeRepository.save(confirms);
		mapToResponse(updatedTrade);
	}

	@Override
	public void deleteTrade(Long id, String partnerUuid) {
		Trade confirms = tradeRepository.findById(id)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.CONFIRMS_NOT_FOUND));

		if (!confirms.getPartnerId().equals(partnerUuid)) {
			throw new BaseException(BaseResponseStatus.UNAUTHORIZED_ACCESS);
		}

		tradeRepository.delete(confirms);
	}

	@Override
	public void updateTradeStatus(Long id, String userUuid) {
		Trade trade = tradeRepository.findById(id)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.CONFIRMS_NOT_FOUND));

		if (!trade.getUserId().equals(userUuid)) {
			throw new BaseException(BaseResponseStatus.UNAUTHORIZED_ACCESS);
		}
		trade.updateStatus();
		Trade updatedConfirms = tradeRepository.save(trade);
		mapToResponse(updatedConfirms);
	}

	private Trade mapToEntity(TradeRequest tradeRequest) {
		return Trade.builder()
			.partnerId(tradeRequest.getPartnerId())
			.userId(tradeRequest.getUserId())
			.options(tradeRequest.getOptions())
			.totalPrice(tradeRequest.getTotalPrice())
			.dueDate(tradeRequest.getDueDate())
			.requestId(tradeRequest.getRequestId())
			.build();
	}

	private TradeResponse mapToResponse(Trade confirms) {
		return TradeResponse.builder()
			.id(confirms.getTradeId())
			.partnerId(confirms.getPartnerId())
			.userId(confirms.getUserId())
			.options(confirms.getOptions())
			.totalPrice(confirms.getTotalPrice())
			.dueDate(confirms.getDueDate())
			.requestId(confirms.getRequestId())
			.status(confirms.getStatus())
			.build();
	}
}