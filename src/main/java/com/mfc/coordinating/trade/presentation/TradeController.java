package com.mfc.coordinating.trade.presentation;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mfc.coordinating.common.response.BaseResponse;
import com.mfc.coordinating.trade.application.TradeService;
import com.mfc.coordinating.trade.dto.request.TradeRequest;
import com.mfc.coordinating.trade.dto.request.TradeUpdateRequest;
import com.mfc.coordinating.trade.dto.response.TradeResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trade")
@Tag(name = "Trade", description = "코디와 유저 매칭 후 최종 내역(채팅방 확정서)")

public class TradeController {
	private final TradeService tradeService;

	@PostMapping
	@Operation(summary = "제안서 생성", description = "새로운 제안서를 생성합니다.")
	public BaseResponse<TradeResponse> createTrade(@RequestBody TradeRequest request,
		@RequestHeader("Partner-UUID") String partnerUuid) {
		TradeResponse confirmsResponse = tradeService.createTrade(request, partnerUuid);
		return new BaseResponse<>(confirmsResponse);
	}

	@GetMapping("/partner/{id}")
	@Operation(summary = "제안서 조회 (파트너)", description = "파트너가 제안서를 조회합니다.")
	public BaseResponse<TradeResponse> getTradeByPartner(@PathVariable Long id,
		@RequestHeader("Partner-UUID") String partnerUuid) {
		TradeResponse confirmsResponse = tradeService.getTradeById(id, partnerUuid);
		return new BaseResponse<>(confirmsResponse);
	}

	@GetMapping("/user/{id}")
	@Operation(summary = "제안서 조회 (유저)", description = "유저가 제안서를 조회합니다.")
	public BaseResponse<TradeResponse> getTradeByUser(@PathVariable Long id,
		@RequestHeader("User-UUID") String userUuid) {
		TradeResponse confirmsResponse = tradeService.getTradeById(id, userUuid);
		return new BaseResponse<>(confirmsResponse);
	}


	@PutMapping("/{id}")
	@Operation(summary = "제안서 수정", description = "제안서 정보를 수정합니다.")
	public BaseResponse<Void> updateTrade(@PathVariable Long id,
		@RequestBody TradeUpdateRequest request,
		@RequestHeader("Partner-UUID") String partnerUuid) {
		tradeService.updateTrade(id, request, partnerUuid);
		return new BaseResponse<>();
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "제안서 삭제", description = "제안서를 삭제합니다.")
	public BaseResponse<Void> deleteTrade(@PathVariable Long id,
		@RequestHeader("Partner-UUID") String partnerUuid) {
		tradeService.deleteTrade(id, partnerUuid);
		return new BaseResponse<>();
	}

	@PutMapping("/user/{id}")
	@Operation(summary = "제안 수락", description = "코디네이터가 제출한 코디를 보고 최종 확정 여부 결정(파트너에게 캐시 최종 전달)")
	public BaseResponse<Void> updateTradeStatus(@PathVariable Long id,
		@RequestHeader("User-UUID") String userUuid) {
		tradeService.updateTradeStatus(id, userUuid);
		return new BaseResponse<>();
	}
}