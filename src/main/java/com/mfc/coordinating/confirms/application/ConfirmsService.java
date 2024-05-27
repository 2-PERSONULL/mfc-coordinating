package com.mfc.coordinating.confirms.application;

import com.mfc.coordinating.confirms.dto.request.ConfirmsRequest;
import com.mfc.coordinating.confirms.dto.request.ConfirmsUpdateRequest;
import com.mfc.coordinating.confirms.dto.response.ConfirmsResponse;

public interface ConfirmsService {
	ConfirmsResponse createConfirms(ConfirmsRequest confirmsRequest, String partnerUuid);
	ConfirmsResponse getConfirmsById(Long id, String uuid);
	void updateConfirms(Long id, ConfirmsUpdateRequest updatedConfirmsRequest, String partnerUuid);
	void deleteConfirms(Long id, String partnerUuid);
	void updateConfirmsStatus(Long id, String userUuid);
}
