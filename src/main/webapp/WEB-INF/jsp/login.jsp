<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/include/header.jsp"%>

	<!-- contents -->
	<div id="contents" class="contents sub_contents">
		<!-- page root -->
		<div class="page_root">
			<div class="inner dpib_wrap text_right">
				<a href="<c:url value="/index.do" />" title="홈"></a>
				<a href="<c:url value="/login.do" />" title="로그인" class="jade">로그인</a>
			</div>
		</div>
		<!-- // page root -->

		<!-- page title -->
		<div class="page_title">
			<p>로그인</p>
		</div>
		<!-- // page title -->

		<!-- 콘텐츠 시작 -->
		<div class="inner login text_center">
			<p class="sub_text">
				<b>로그인을 해주세요.</b>
				이곳에서 다양한 서비스를 경험하세요
			</p>

			<div class="login_box">
				<form name="loginForm" action="/auth.do" method="post">
					<!-- csrf 토큰(?) -->
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<div class="login_form">
						<input type="text" name="userId" class="" value="" placeholder="아이디를 입력해 주세요." />
						<input type="password" name="userPw" class="" value="" placeholder="비밀번호를 입력해 주세요." />
						<input type="submit" class="btn btn_jade" value="로그인" />
					</div>
				</form>
				<span>${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message }</span>
				<ul>
					<li><a href="javascript:void(0)" title="아이디 찾기">아이디 찾기</a></li>
					<li><a href="javascript:void(0)" title="비밀번호 찾기">비밀번호 찾기</a></li>
					<li><a href="/member/join.do" title="회원가입">회원가입</a></li>
				</ul>
			</div>
		</div>
		<!-- // 콘텐츠 끝 -->
	</div>
	<!-- // contents -->

<%-- <form name="loginForm" action="/auth.do" method="post"
	onsubmit="checkForm(this)">
	<input type="hidden" name="${_csrf.parameterName}"
		value="${_csrf.token}" />
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
</form> --%>

<script>
	function checkForm(obj) {
		return (checkField(obj.userId, "아이디")
			 && checkField(obj.userPw, "패스워드"));
	}
</script>

<%@include file="/WEB-INF/include/footer.jsp"%>
