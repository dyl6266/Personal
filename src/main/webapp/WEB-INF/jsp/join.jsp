<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/include/header.jsp"%>

	<!-- contents -->
	<div id="contents" class="contents sub_contents">
		<!-- page root -->
		<div class="page_root">
			<div class="inner dpib_wrap text_right">
				<a href="<c:url value="/index.do" />" title="홈"></a>
				<a href="<c:url value="/member/join.do" />" title="회원가입" class="jade">회원가입</a>
			</div>
		</div>
		<!-- // page root -->

		<!-- page title -->
		<div class="page_title">
			<p>회원가입</p>
		</div>
		<!-- // page title -->

		<form name="joinForm" action="<c:url value="/member/processing.do?type=register" />" method="post" onsubmit="return checkForm(this)">
			<!-- 콘텐츠 시작 -->
			<div class="inner signup text_center form_write">
				<table>
					<caption>회원가입 폼</caption>
					<colgroup>
						<col style="width: 20%;">
						<col style="width: 80%;">
					</colgroup>
					<tbody>
						<tr>
							<th><b>*</b> 아이디</th>
							<td>
								<input type="hidden" name="userId" />
								<input type="text" name="frontAddr" class="type2" maxlength="15" />
								<span>@</span>
								<input type="text" name="backAddr" class="type2" readonly />
								<select onchange="changeDomain(this)">
									<option value="">도메인 선택</option>
									<option value="naver.com">naver.com</option>
									<option value="hanmail.net">hanmail.net</option>
									<option value="nate.com">nate.com</option>
									<option value="gmail.com">gmail.com</option>
									<option value="daum.net">daum.net</option>
									<option value="hotmail.com">hotmail.com</option>
									<option value="lycos.co.kr">lycos.co.kr</option>
									<option value="korea.com">korea.com</option>
									<option value="dreamwiz.com">dreamwiz.com</option>
									<option value="yahoo.com">yahoo.com</option>
									<option value="ymail.com">ymail.com</option>
									<option value="live.com">live.com</option>
									<option value="aol.com">aol.com</option>
									<option value="msn.com">msn.com</option>
									<option value="me.com">me.com</option>
									<option value="icloud.com">icloud.com</option>
									<option value="rocketmail">rocketmail.com</option>
									<option value="qq.com">qq.com</option>
									<option value="link.com">link.com</option>
									<option value="direct">직접 입력</option>
								</select>
							</td>
						</tr>
						<tr>
							<th><b>*</b> 비밀번호</th>
							<td><input type="password" name="userPw" maxlength="20" placeholder="영문자, 숫자, 특수문자 조합 8~20자리로 입력해 주세요." /></td>
						</tr>
						<tr>
							<th><b>*</b> 비밀번호 재입력</th>
							<td><input type="password" name="userPwCheck" maxlength="20" placeholder="비밀번호를 다시 입력해 주세요." /></td>
						</tr>
						<tr>
							<th><b>*</b> 이름</th>
							<td><input type="text" name="userName" class="type2" placeholder="이름을 입력해 주세요." /></td>
						</tr>
						<tr>
							<th><b>*</b> 연락처</th>
							<td><input type="text" name="userPhone" maxlength="11" placeholder="'ㅡ' 없이 숫자만 입력해 주세요." /></td>
						</tr>
					</tbody>
				</table>
				<div class="btn_wrap text_center">
					<a href="javascript:void(0)" class="btn btn_jade" onclick="moveNextStepAndSendMail()">다음</a>
					<a href="<c:url value="/index.do" />" class="btn btn_gray ml05">취소</a>
				</div>
			</div>
			<!-- // 콘텐츠 끝 -->
		</form>

		<form name="authForm" action="<c:url value="" />" method="post" onsubmit="checkAuthForm(this)">
			<!-- Step2 -->
			<div class="inner signup text_center form_write">
				<h1>메일로 발송된 8자리 인증번호를 10분 이내에 입력해 주세요.</h1><br />
				<input type="text" name="authKey" class="type2" maxlength="8" placeholder="인증번호 입력" style="text-align: center;" />

				<div class="btn_wrap text_center">
					<a href="javascript:void(0)" class="btn btn_jade" onclick="checkAuthKey()">다음</a>
					<!-- <button type="submit" class="btn btn_jade">다음</button> -->
				</div>
			</div>
			<!-- //. Step2 -->
		</form>
	</div>
	<!-- // contents -->

	<script>
		/* 정보 입력 폼 */
		var joinForm = document.joinForm;
		/* 인증번호 입력 폼 */
		var authForm = document.authForm;

		$(function() {
			$(authForm).hide();
		});

		/**
		 * 도메인(이메일) 변경
		 */
		function changeDomain(obj) {
			/* 직접 입력 */
			if (obj.value == "direct") {
				joinForm.backAddr.value = "";
				joinForm.backAddr.removeAttribute("readonly");
				joinForm.backAddr.focus();
			} else {
				joinForm.backAddr.value = obj.value;
				joinForm.backAddr.setAttribute("readonly", "true");
			}
		}

		function moveNextStepAndSendMail() {
			/* 아이디 hidden */
			joinForm.userId.value = joinForm.frontAddr.value + '@' + joinForm.backAddr.value;

			var status = checkValidation(joinForm.frontAddr, "아이디", null, null)
					  && checkValidation(joinForm.backAddr, "도메인", null, null)
					  && checkValidation(joinForm.userId, null, joinForm.userId, "email")
					  && checkValidation(joinForm.userPw, "비밀번호", null, null)
					  && checkValidation(joinForm.userPw, null, null, "password")
					  && checkEquals(joinForm.userPw, joinForm.userPwCheck, "비밀번호와 재입력한 비밀번호")
					  && checkValidation(joinForm.userName, "이름", null, null)
					  && checkValidation(joinForm.userPhone, "연락처", null, null)
					  && checkValidation(joinForm.userPhone, null, null, "phone");

			if ( status == true ) {
				sendAuthMail();
			}

			return status;
		}

		/**
		 * 인증 메일 발송
		 */
		function sendAuthMail() {
			var uri = '<c:url value="/member/sendAuthMail.do" />';

			$.ajax({
				url : uri,
				type : "post",
				dataType : "json",
				headers : {
// 					"Content-type" : "application/json",
					"X-HTTP-Method-Override" : "post",
					"${_csrf.headerName}" : "${_csrf.token}"
				},
				data : { "userId" : joinForm.userId.value },
				async : false,
				success : function(response) {
					if (isEmpty(response) == false && response.result === "SUCCESS") {
						Swal.fire(response.message);

						$(joinForm).hide();
						$(authForm).show();
					} else {
						Swal.fire("오류가 발생했습니다. 다시 시도해 주세요.");
					}
					return false;
				},
				error : function(response, status, error) {
					Swal.fire(response.responseJSON.message);
					return false;
				}
			});
		}

		/**
		 * 인증번호 체크
		 */
		function checkAuthKey() {
			var uri = '<c:url value="/member/checkAuthKey.do" />';
			var params = {
					"authKey" : authForm.authKey.value,
					"userId" : joinForm.userId.value,
					"userPw" : joinForm.userPw.value,
					"userName" : joinForm.userName.value,
					"userPhone" : joinForm.userPhone.value
			};

			$.ajax({
				url : uri,
				type : "post",
				dataType : "json",
				headers : {
					"Content-type" : "application/json",
					"X-HTTP-Method-Override" : "post",
					"${_csrf.headerName}" : "${_csrf.token}"
				},
				data : JSON.stringify(params),
				async : false,
				success : function(response) {
					if (isEmpty(response) == false && response.result === "SUCCESS") {
						Swal.fire({
							title: response.message + '\n' + ' 로그인 페이지로 이동합니다.',
							confirmButtonColor: '#3085d6',
							confirmButtonText: '로그인하기'
						}).then((result) => {
							if (result.value) {
								location.href = '<c:url value="/login.do" />';
							}
						})
					} else {
						Swal.fire("오류가 발생했습니다. 다시 시도해 주세요.");
					}
					return false;
				},
				error : function(response, status, error) {
					Swal.fire(response.responseJSON.message);
					return false;
				}
			});
		}
	</script>

<%@include file="/WEB-INF/include/footer.jsp"%>
