package com.mfc.coordinating.submissions.entity;

import com.mfc.coordinating.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
@Getter
@Table(name = "submissions")
public class Submissions extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "submission_id")
	private Long submissionId;

	@Column(name = "partnerId", nullable = false)
	private String partnerId;

	@Column(name = "userId", nullable = false)
	private String userId;

	@Column(nullable = false)
	private String category;

	@Column(nullable = false)
	private String brand;

	@Column(nullable = false)
	private Long budget;

	private String url;

	@Column(nullable = false)
	private String comment;

	private String image;

	@Column(nullable = false)
	private Byte save;

	@Column(nullable = false)
	private Long options;

	@Column(name = "request_id", nullable = false)
	private Long requestId;
}
