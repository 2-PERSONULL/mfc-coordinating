package com.mfc.coordinating.requests.dto.kafka;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateChatRoomDto {
	private String requestId;
	private List<String> members;
}
