package com.mfc.coordinating.requests.enums;

public enum RequestsStates {
	WRITING,			// 요청전
	NONERESPONSE,		// 요청(미응답)
	RESPONSEACCEPT,		// 요청수락(응답)
	RESPONSEREJECT,		// 요청거절(응답)
	WAITING,			// 거래 대기
	PROPOSALREJECT,		// 거래 거절
	CONFIRMED,			// 거래 확정
	CLOSING				// 마감(코디완료)
}
