//
// import com.mfc.coordinating.common.entity.BaseEntity;
//
// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.Table;
// import lombok.AccessLevel;
// import lombok.Builder;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.ToString;
//
// @Entity
// @NoArgsConstructor(access = AccessLevel.PROTECTED)
// @ToString
// @Getter
// @Table(name = "requests")
// public class Requests extends BaseEntity {
//
// 	@Id
// 	@GeneratedValue(strategy = GenerationType.IDENTITY)
// 	@Column(name = "request_id")
// 	private Long requestId;
//
// 	@Column(name = "user_id", nullable = false)
// 	private String userId;
//
// 	@Column(nullable = false)
// 	private String title;
//
// 	@Column(nullable = false)
// 	private String description;
//
// 	@Column(nullable = false)
// 	private String situation;
//
// 	@Column(nullable = false)
// 	private Long budget;
//
// 	@Column(nullable = false)
// 	private String otherRequirements;
//
// 	@Builder
// 	public Requests(Long requestId, String userId, String title, String description, String situation,
// 		Long budget, String otherRequirements) {
// 		this.requestId = requestId;
// 		this.userId = userId;
// 		this.title = title;
// 		this.description = description;
// 		this.situation = situation;
// 		this.budget = budget;
// 		this.otherRequirements = otherRequirements;
// 	}
//
// 	public void updateRequests(String title, String description, String situation, Long budget,
// 		String otherRequirements) {
// 		this.title = title;
// 		this.description = description;
// 		this.situation = situation;
// 		this.budget = budget;
// 		this.otherRequirements = otherRequirements;
// 	}
// }

package com.mfc.coordinating.requests.domain;

import java.time.LocalDate;
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
	private String otherRequirements;
	private LocalDate createdDate;

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
	public Requests(String requestId, String userId, String title, String description, String situation, String budget, String otherRequirements, String userImageUrl, String userNickName, Short userGender, int userAge, List<String> brandIds, List<String> categoryIds, List<String> referenceImageUrls, List<String> myImageUrls) {
		this.requestId = requestId;
		this.userId = userId;
		this.title = title;
		this.description = description;
		this.situation = situation;
		this.budget = budget;
		this.otherRequirements = otherRequirements;
		this.userImageUrl = userImageUrl;
		this.userNickName = userNickName;
		this.userGender = userGender;
		this.userAge = userAge;
		this.brandIds = brandIds;
		this.categoryIds = categoryIds;
		this.referenceImageUrls = referenceImageUrls;
		this.myImageUrls = myImageUrls;
		this.createdDate = LocalDate.now();
	}

	public void addPartnerInfo(String partnerId, RequestsStates status, LocalDate deadline) {
		this.partner.add(new RequestPartner(partnerId, status, deadline));
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class RequestPartner {
		private String partnerId;
		private RequestsStates status;
		private LocalDate deadline;
		private String confirmedPrice;


		@Builder
		public RequestPartner(String partnerId, RequestsStates status, LocalDate deadline) {
			this.partnerId = partnerId;
			this.status = status;
			this.deadline = deadline;
		}

		public void updateStatus(RequestsStates status) {
			this.status = status;
		}
	}

	public void updateRequests(String title, String description, String situation, String budget,
		String otherRequirements, List<String> brandIds, List<String> categoryIds,
		List<String> referenceImageUrls, List<String> myImageUrls) {
		this.title = title;
		this.description = description;        this.situation = situation;
		this.budget = budget;
		this.otherRequirements = otherRequirements;
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
		// Handle case where partnerId is not found (optional)
	}

}


