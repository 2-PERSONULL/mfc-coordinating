package com.mfc.coordinating.trade.application;

import com.mfc.coordinating.trade.dto.request.TradeRequest;
import com.mfc.coordinating.trade.dto.request.TradeUpdateRequest;
import com.mfc.coordinating.trade.dto.response.TradeResponse;

/**
 * TradeService 인터페이스는 트레이드 관련 비즈니스 로직을 정의합니다.
 */
public interface TradeService {

	/**
	 * 새로운 트레이드를 생성합니다.
	 *
	 * @param tradeRequest 트레이드 생성에 필요한 정보
	 * @param partnerUuid 파트너의 고유 식별자
	 * @return 생성된 트레이드 정보
	 */
	TradeResponse createTrade(TradeRequest tradeRequest, String partnerUuid);

	/**
	 * 주어진 ID로 트레이드를 조회합니다.
	 *
	 * @param id 조회할 트레이드의 ID
	 * @param uuid 요청자(파트너 또는 유저)의 고유 식별자
	 * @return 조회된 트레이드 정보
	 */
	TradeResponse getTradeById(Long id, String uuid);

	/**
	 * 기존 트레이드 정보를 업데이트합니다.
	 *
	 * @param id 업데이트할 트레이드의 ID
	 * @param updatedTradeRequest 업데이트할 트레이드 정보
	 * @param partnerUuid 파트너의 고유 식별자
	 */
	void updateTrade(Long id, TradeUpdateRequest updatedTradeRequest, String partnerUuid);

	/**
	 * 트레이드를 삭제합니다.
	 *
	 * @param id 삭제할 트레이드의 ID
	 * @param partnerUuid 파트너의 고유 식별자
	 */
	void deleteTrade(Long id, String partnerUuid);

	/**
	 * 트레이드의 상태를 업데이트합니다 (수락).
	 * 이 메서드는 requestId를 사용하여 트레이드를 식별합니다.
	 *
	 * @param requestId 업데이트할 트레이드의 요청 ID
	 * @param userUuid 유저의 고유 식별자
	 */
	void updateTradeStatus(String requestId, String userUuid);

	/**
	 * 트레이드 만료 처리를 합니다.
	 *
	 * @param tradeId 만료 처리할 트레이드의 ID
	 */
	void handleTradeExpired(Long tradeId);
}