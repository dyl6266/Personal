package com.dy.common.utils;

import java.util.Locale;

import org.springframework.context.support.MessageSourceAccessor;

public class MessageUtils {

	private static MessageSourceAccessor msAccessor = null;

	public static MessageSourceAccessor getMsAccessor() {
		return msAccessor;
	}

	public void setMessageSourceAccessor(MessageSourceAccessor msAccessor) {
		MessageUtils.msAccessor = msAccessor;
	}

	public static String getMessage(String code) {
		return msAccessor.getMessage(code, Locale.getDefault());
	}

	public static String getMessage(String code, Object[] objs) {
		return msAccessor.getMessage(code, objs, Locale.getDefault());
	}

}
