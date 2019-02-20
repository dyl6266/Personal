<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=yes" />
		<title>Personal</title>

		<!-- Js -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
		<!-- Sweet Alert CDN -->
		<script src="https://cdn.jsdelivr.net/npm/sweetalert2@8"></script>
		<script src="/resources/js/jquery.bxslider.js" /></script>
		<script src="/resources/js/common.js" /></script>
	</head>
	<body>
		<c:if test="${empty(params) == false }">
			<form name="dataForm" action="<c:url value="${redirectURI }" />" method="post">
				<c:forEach var="row" items="${params }" varStatus="status">
					<input type="hidden" name="${row.key }" value="${row.value }" />
				</c:forEach>
			</form>
		</c:if>
		<script>
			var message = "${message }";
			var redirectURI = "${redirectURI }";
			var params = "${params }";

			$(function() {
				Swal.fire(message);

				if ( isEmpty(params) == false ) {
					document.dataForm.submit();
				} else {
					location.href = redirectURI;
				}
			});
		</script>
	</body>
</html>
