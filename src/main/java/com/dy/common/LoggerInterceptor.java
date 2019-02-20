package com.dy.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoggerInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);

	/**
	 * 컨트롤러보다 먼저 수행되는 메소드
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		logger.info("==================== START ====================");
		logger.info("Request URI ::: " + request.getRequestURI());

		return super.preHandle(request, response, handler);
	}

	/**
	 * 컨트롤러 로직을 수행하고, 화면이 보여지기 직전에 수행되는 메소드
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		logger.info("==================== END ====================");

		super.postHandle(request, response, handler, modelAndView);
	}

}
