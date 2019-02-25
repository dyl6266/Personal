<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix=c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<a href="<c:url value="/admin/test.do" />">관리자</a>
	<a href="<c:url value="/member/test.do" />">회원</a>
	<a href="<c:url value="/board/test.do" />">사용자</a>
</body>
</html>