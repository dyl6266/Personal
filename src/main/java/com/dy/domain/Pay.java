//package com.dy.domain;
//
//public enum Pay {
//
//	TOSS("토스"), PAYCO("페이코"), CARD("신용카드"), KAKAO_PAY("카카오페이"), POINT("포인트"), EMPTY("업음");
//
//	private String title;
//
//	private Pay(String title) {
//		this.title = title;
//	}
//
//	public String getTitle() {
//		return title;
//	}
//
//	public static Pay test(String title) {
//		switch (title) {
//		case "토스":
//			return Pay.TOSS;
//		
//		case "페이코":
//			return Pay.PAYCO;
//		default:
//			throw new AssertionError("Unknown gender : " + title);
//		}
//	}
//}
