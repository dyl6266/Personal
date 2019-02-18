<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<!DOCTYPE html>
<html lang="ko">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=yes" />
		<title>Personal</title>

		<!-- Css -->
		<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
		<link rel="stylesheet" type="text/css" href="/resources/css/style.css" />
		<link rel="stylesheet" type="text/css" href="/resources/css/media.css" />
		<link rel="stylesheet" type="text/css" href="/resources/css/jquery.bxslider.css" />
		<link rel="icon" href="data:;base64,iVBORw0KGgo=">

		<!-- Js -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
		<!-- 다음 주소 API CDN -->
		<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
		<script src="/resources/js/ui.js" /></script>
		<script src="/resources/js/jquery.bxslider.js" /></script>
		<script src="/resources/js/common.js" /></script>
	</head>
	<body>
		<div id="wrapper">
			<!-- header -->
			<header>
				<c:set var="requestURI" value="${pageContext.request.requestURI }" />
				<div class="header_wrap <c:if test="${fn:contains(requestURI, 'index.jsp') }"> main_header</c:if>">
					<div class="inner fl_wrap">
						<!-- Logo -->
						<a href="<c:url value="/index.do" />" class="logo text_blind fl_left">MICHUHOL ART MARKET 로고</a>
						<!-- // Logo -->

						<!-- Gnb -->
						<div class="gnb_wrap fl_right">
							<div class="gnb">
								<ul class="depth1">
									<li>
										<a href="artmarket/info.html" title="미추홀 아트마켓" class="mobile_none">미추홀 아트마켓</a>
										<a href="#none" title="아트마켓" class="mobile_block" onclick="menuOpen(this);">아트마켓</a>
										<ul class="depth2">
											<li><a href="artmarket/info.html" title="소개">소개</a></li>
											<li><a href="artmarket/schedule.html" title="아트마켓일정">아트마켓일정</a></li>
											<li><a href="artmarket/seller_apply.html" title="셀러신청">셀러신청</a></li>
										</ul>
									</li>
									<li>
										<a href="onlinemarket/info.html" title="온라인마켓" class="mobile_none">온라인마켓</a>
										<a href="#none" title="온라인마켓" class="mobile_block" onclick="menuOpen(this);">온라인마켓</a>
										<ul class="depth2">
											<li><a href="onlinemarket/info.html" title="소개">소개</a></li>
											<li><a href="onlinemarket/product_list.html" title="입점상품">입점상품</a></li>
											<li><a href="onlinemarket/shop_list.html" title="입점상점">입점상점</a></li>
											<li><a href="onlinemarket/application.html" title="입점신청">입점신청</a></li>
										</ul>
									</li>
									<li>
										<a href="touristspot/food_list.html" title="주변관광지" class="mobile_none">주변관광지</a>
										<a href="#none" title="주변관광지" class="mobile_block" onclick="menuOpen(this);">주변관광지</a>
										<ul class="depth2">
											<li><a href="touristspot/food_list.html" title="맛집">맛집</a></li>
											<li><a href="touristspot/spot_list.html" title="관광지">관광지</a></li>
										</ul>
									</li>
									<li>
										<a href="notice/notice_list.html" title="소식" class="mobile_none">소식</a>
										<a href="#none" title="소식" class="mobile_block" onclick="menuOpen(this);">소식</a>
										<ul class="depth2">
											<li><a href="notice/notice_list.html" title="공지사항">공지사항</a></li>
											<li><a href="notice/sketch_list.html" title="현장스케치">현장스케치</a></li>
											<li><a href="notice/report_list.html" title="언론보도">언론보도</a></li>
										</ul>
									</li>
									<li class="artAvenue">
										<a href="artavenue/artavenue_info.html" title="아트에비뉴27" class="mobile_none">아트에비뉴27</a>
										<a href="#none" title="아트에비뉴27" class="mobile_block">아트에비뉴27</a>
										<ul class="depth2">
											<li><a href="artavenue/artavenue_info.html" title="소개">소개</a></li>
											<li><a href="artavenue/month_art.html" title="이달의아트에비뉴27">이달의아트에비뉴27</a></li>
											<li><a href="artavenue/rental_apply.html" title="대관신청">대관신청</a></li>
										</ul>
									</li>
									<li class="utill fl_wrap">
										<a href="/login.do" title="로그인" class="text_blind login">로그인</a> <!-- 아이콘변경 class => login(로그인) / logout(로그아웃) -->
										<a href="/member/join.do" title="회원가입" class="text_blind mobile_block join">회원가입</a> <!-- 아이콘변경 class => join(회원가입) / mypage(마이페이지) -->
										<ul class="depth2">
											<li><a href="/login.do" title="로그인">로그인</a></li>
											<li><a href="/member/join.do" title="회원가입">회원가입</a></li>
											<li><a href="member/logout.html" title="로그아웃">로그아웃</a></li>
											<li><a href="mypage/mypage.html" title="마이페이지">마이페이지</a></li>
										</ul>
									</li>
								</ul>
								<a href="#none" class="mobile_block btn_gnb_close text_blind" onclick="gnbClose(this);">닫기</a>
							</div>
							<div class="gnb_bg" onclick="gnbClose(this);">
								<div class="inner"></div>
							</div>
						</div>
						<!-- // Gnb -->
	
						<!-- Mobile gnb button -->
						<div class="btn_gnb mobile_block fl_right">
							<a href="#none" class="text_blind" onclick="gnbOpen(this);">메뉴</a>
						</div>
						<!-- // Mobile gnb button -->
					</div>
				</div>
			</header>
			<!-- // header -->
