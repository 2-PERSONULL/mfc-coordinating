package com.mfc.coordinating.confirms.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mfc.coordinating.common.exception.BaseException;
import com.mfc.coordinating.common.response.BaseResponseStatus;
import com.mfc.coordinating.confirms.domain.Confirms;
import com.mfc.coordinating.confirms.dto.request.ConfirmsRequest;
import com.mfc.coordinating.confirms.dto.request.ConfirmsUpdateRequest;
import com.mfc.coordinating.confirms.dto.response.ConfirmsResponse;
import com.mfc.coordinating.confirms.infrastructure.ConfirmsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ConfirmsServiceImpl implements ConfirmsService {

	private final ConfirmsRepository confirmsRepository;

	@Override
	public ConfirmsResponse createConfirms(ConfirmsRequest confirmsRequest, String partnerUuid) {
		if (!confirmsRequest.getPartnerId().equals(partnerUuid)) {
			throw new BaseException(BaseResponseStatus.UNAUTHORIZED_ACCESS);
		}
		Confirms confirms = mapToEntity(confirmsRequest);
		Confirms createdConfirms = confirmsRepository.save(confirms);
		return mapToResponse(createdConfirms);
	}

	@Override
	@Transactional(readOnly = true)
	public ConfirmsResponse getConfirmsById(Long id, String uuid) {
		Confirms confirms = confirmsRepository.findById(id)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.CONFIRMS_NOT_FOUND));

		if (!confirms.getPartnerId().equals(uuid) && !confirms.getUserId().equals(uuid)) {
			throw new BaseException(BaseResponseStatus.UNAUTHORIZED_ACCESS);
		}

		return mapToResponse(confirms);
	}

	@Override
	public void updateConfirms(Long id, ConfirmsUpdateRequest updatedConfirmsRequest, String partnerUuid) {
		Confirms confirms = confirmsRepository.findById(id)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.CONFIRMS_NOT_FOUND));

		if (!confirms.getPartnerId().equals(partnerUuid)) {
			throw new BaseException(BaseResponseStatus.UNAUTHORIZED_ACCESS);
		}

		confirms.updateConfirms(updatedConfirmsRequest.getDueDate(), updatedConfirmsRequest.getTotalPrice(),
			updatedConfirmsRequest.getOptions());
		Confirms updatedConfirms = confirmsRepository.save(confirms);
		mapToResponse(updatedConfirms);
	}

	@Override
	public void deleteConfirms(Long id, String partnerUuid) {
		Confirms confirms = confirmsRepository.findById(id)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.CONFIRMS_NOT_FOUND));

		if (!confirms.getPartnerId().equals(partnerUuid)) {
			throw new BaseException(BaseResponseStatus.UNAUTHORIZED_ACCESS);
		}

		confirmsRepository.delete(confirms);
	}

	@Override
	public void updateConfirmsStatus(Long id, String userUuid) {
		Confirms confirms = confirmsRepository.findById(id)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.CONFIRMS_NOT_FOUND));

		if (!confirms.getUserId().equals(userUuid)) {
			throw new BaseException(BaseResponseStatus.UNAUTHORIZED_ACCESS);
		}
		confirms.updateStatus();
		Confirms updatedConfirms = confirmsRepository.save(confirms);
		mapToResponse(updatedConfirms);
	}

	private Confirms mapToEntity(ConfirmsRequest confirmsRequest) {
		return Confirms.builder()
			.partnerId(confirmsRequest.getPartnerId())
			.userId(confirmsRequest.getUserId())
			.options(confirmsRequest.getOptions())
			.totalPrice(confirmsRequest.getTotalPrice())
			.dueDate(confirmsRequest.getDueDate())
			.requestId(confirmsRequest.getRequestId())
			.build();
	}

	private ConfirmsResponse mapToResponse(Confirms confirms) {
		return ConfirmsResponse.builder()
			.id(confirms.getConfirmId())
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