<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/include/header.jsp"%>

<form name="loginForm" action="/auth.do" method="post" onsubmit="checkForm(this)">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	<table>
		<thead>
			<tr>
				<th>아이디</th>
				<th>패스워드</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><input type="text" name="memberId" /></td>
				<td><input type="password" name="memberPw" /></td>
			</tr>
		</tbody>
	</table>
	
	<input type="submit" class="btn" value="로그인" />
</form>

<script>
	function checkForm(obj) {
		return (checkField(obj.memberId, "아이디")
			 && checkField(obj.memberPw, "패스워드"));
	}
</script>

<%@include file="/WEB-INF/include/footer.jsp"%>
