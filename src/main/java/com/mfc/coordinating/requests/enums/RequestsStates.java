package com.mfc.coordinating.requests.enums;

public enum RequestsStates {
	RESPONSE,			// 요청(응답)
	NONERESPONSE,		// 요청(미응답)
	WAITING,			// 거래 대기
	CONFIRMED,			// 거래 확정
	CLOSING				// 마감(코디완료)
}
