package com.mfc.coordinating.requests.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mfc.coordinating.requests.enums.RequestsStates;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "requests")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Requests {
	@Id
	private String requestId;
	private String userId;
	private String title;
	private String description;
	private String situation;
	private String budget;
	private Instant createdDate;

	private String userImageUrl;
	private String userNickName;
	private Short userGender;
	private int userAge;

	private List<RequestPartner> partner = new ArrayList<>();
	private List<String> brandIds = new ArrayList<>();
	private List<String> categoryIds = new ArrayList<>();
	private List<String> referenceImageUrls = new ArrayList<>();
	private List<String> myImageUrls = new ArrayList<>();

	@Builder
	public Requests(String requestId, String userId, String title, String description, String situation,
		String budget, String userImageUrl, String userNickName,
		Short userGender, int userAge, List<String> brandIds, List<String> categoryIds,
		List<String> referenceImageUrls, List<String> myImageUrls) {
		this.requestId = requestId;
		this.userId = userId;
		this.title = title;
		this.description = description;
		this.situation = situation;
		this.budget = budget;
		this.userImageUrl = userImageUrl;
		this.userNickName = userNickName;
		this.userGender = userGender;
		this.userAge = userAge;
		this.brandIds = brandIds;
		this.categoryIds = categoryIds;
		this.referenceImageUrls = referenceImageUrls;
		this.myImageUrls = myImageUrls;
		this.createdDate = Instant.now();
	}

	public void addPartnerInfo(String partnerId, RequestsStates status, Instant deadline) {
		this.partner.add(new RequestPartner(partnerId, status, deadline));
	}

	public void updateConfirmedProposal(String partnerId, Double price, Instant confirmDate) {
		for (RequestPartner partner : this.partner) {
			if (partner.getPartnerId().equals(partnerId)) {
				partner.updateConfirmedProposal(price, confirmDate);
				return;
			}
		}
	}


	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class RequestPartner {
		private String partnerId;
		private RequestsStates status;
		private Instant deadline;
		private Double confirmedPrice;


		@Builder
		public RequestPartner(String partnerId, RequestsStates status, Instant deadline) {
			this.partnerId = partnerId;
			this.status = status;
			this.deadline = deadline;
		}

		public void updateStatus(RequestsStates status) {
			this.status = status;
		}

		public void updateConfirmedProposal(Double price, Instant confirmDate) {
			this.confirmedPrice = price;
			this.deadline = confirmDate;
			this.status = RequestsStates.WAITING;
		}

	}

	public void updateRequests(String title, String description, String situation, String budget,
		List<String> brandIds, List<String> categoryIds,
		List<String> referenceImageUrls, List<String> myImageUrls) {
		this.title = title;
		this.description = description;        this.situation = situation;
		this.budget = budget;
		this.brandIds = brandIds;
		this.categoryIds = categoryIds;
		this.referenceImageUrls = referenceImageUrls;
		this.myImageUrls = myImageUrls;
	}

	public void updatePartnerStatus(String partnerId, RequestsStates status) {
		for (RequestPartner partner : this.partner) {
			if (partner.getPartnerId().equals(partnerId)) {
				partner.updateStatus(status);
				return;
			}
		}
	}

}


