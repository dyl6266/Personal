<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Personal</title>

<link rel="stylesheet" type="text/css" href="/resources/css/style.css" />

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="/resources/js/common.js"></script>
</head>
<body>
	<div>
		<sec:authorize access="isAnonymous()">
			<a href="/login.do">로그인</a>
		</sec:authorize>
		<sec:authorize access="isAuthenticated()">
			<form name="logoutForm" action="/logout.do" method="post">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<input type="submit" value="로그아웃" />
			</form>
		</sec:authorize>
	</div>