// package com.mfc.coordinating.requests.domain;
//
// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
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
// @Table(name = "brand")
// public class Brand {
//
// 	@Id
// 	@GeneratedValue(strategy = GenerationType.IDENTITY)
// 	@Column(name = "brand_id")
// 	private Long brandId;
//
// 	@Column(nullable = false)
// 	private String name;
//
// 	@ManyToOne
// 	@JoinColumn(name = "request_id")
// 	private Requests requests;
//
// 	@Builder
// 	public Brand(String name, Requests requests) {
// 		this.name = name;
// 		this.requests = requests;
// 	}
// }