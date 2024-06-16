package com.mfc.coordinating.requests.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.mfc.coordinating.requests.domain.Requests;
import com.mfc.coordinating.requests.dto.res.MyRequestListResponse;
import com.mfc.coordinating.requests.dto.res.RequestsListResDto;

@Component
public class RequestMapper {
	private final ModelMapper modelMapper;

	public RequestMapper() {
		this.modelMapper = new ModelMapper();
		configureMapper();
	}

	private void configureMapper() {
		modelMapper.getConfiguration()
			.setMatchingStrategy(MatchingStrategies.STRICT)
			.setFieldMatchingEnabled(true)
			.setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
	}

	public RequestsListResDto toRequestsListResDto(Requests requests, String partnerId) {
		Requests.RequestPartner partner = requests.getPartner().stream()
			.filter(p -> p.getPartnerId().equals(partnerId))
			.findFirst()
			.orElse(null);

		if (partner == null) {
			return null;
		}

		return RequestsListResDto.builder()
			.requestId(requests.getRequestId())
			.userId(requests.getUserId())
			.userImageUrl(requests.getUserImageUrl())
			.userNickName(requests.getUserNickName())
			.userGender(requests.getUserGender())
			.userAge(requests.getUserAge())
			.title(requests.getTitle())
			.createdDate(requests.getCreatedDate())
			.partnerId(partner.getPartnerId())
			.status(partner.getStatus())
			.deadline(partner.getDeadline())
			.build();
	}

	public MyRequestListResponse toMyRequestListResponse(Requests requests) {
		return MyRequestListResponse.builder()
			.requestId(requests.getRequestId())
			.title(requests.getTitle())
			.build();
	}
}