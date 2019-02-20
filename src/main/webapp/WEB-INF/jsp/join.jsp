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
								<input type="hidden" name="memberId" />
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
							<td><input type="password" name="memberPw" maxlength="20" placeholder="영문자, 숫자, 특수문자 조합 8~20자리로 입력해 주세요." /></td>
						</tr>
						<tr>
							<th><b>*</b> 비밀번호 재입력</th>
							<td><input type="password" name="memberPwCheck" maxlength="20" placeholder="비밀번호를 다시 입력해 주세요." /></td>
						</tr>
						<tr>
							<th><b>*</b> 이름</th>
							<td><input type="text" name="memberName" class="type2" placeholder="이름을 입력해 주세요." /></td>
						</tr>
						<tr>
							<th><b>*</b> 연락처</th>
							<td><input type="text" name="memberPhone" maxlength="11" placeholder="'ㅡ' 없이 숫자만 입력해 주세요." /></td>
						</tr>
						<!-- <tr>
							<th><b>*</b> 주소</th>
							<td>
								<input type="text" name="postcode" id="postcode" class="type3" value="" placeholder="우편번호" readonly />
								<input type="button" class="btn btn_border_gray" value="주소찾기" onclick="findPostcode()" /><br />
								<input type="text" name="address" id="address" class="mt05" value="" placeholder="주소" readonly /><br />
								<input type="text" name="detailAddress" id="detailAddress" class="mt05" value="" placeholder="상세주소" />
							</td>
						</tr> -->
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
				<h1>메일로 발송된 8자리 인증번호를 입력해 주세요.</h1><br />
				<input type="text" name="authKey" class="type2" placeholder="인증번호 입력" style="text-align: center;" />
				
				<div class="btn_wrap text_center">
					<a href="javascript:void(0)" class="btn btn_jade" onclick="checkAuthKey()">다음</a>
					<!-- <button type="submit" class="btn btn_jade">다음</button> -->
					<!-- <a href="javascript:void(0)" class="btn btn_gray ml05" onclick="movePrevStep()">이전</a> -->
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
			joinForm.memberId.value = joinForm.frontAddr.value + "@" + joinForm.backAddr.value;

			var status = checkValidation(joinForm.frontAddr, "아이디", null, null)
					  && checkValidation(joinForm.backAddr, "도메인", null, null)
					  && checkValidation(joinForm.memberId, null, joinForm.frontAddr, "email")
					  && checkValidation(joinForm.memberPw, "비밀번호", null, null)
					  && checkValidation(joinForm.memberPw, null, null, "password")
					  && checkEquals(joinForm.memberPw, joinForm.memberPwCheck, "비밀번호와 재입력한 비밀번호")
					  && checkValidation(joinForm.memberName, "이름", null, null)
					  && checkValidation(joinForm.memberPhone, "연락처", null, null)
					  && checkValidation(joinForm.memberPhone, null, null, "phone");

			if ( status == true ) {
				$(joinForm).hide();
				$(authForm).show();

				var uri = '<c:url value="/member/sendMail.do" />';
				console.log(joinForm.memberId.value);
				$.ajax({
					url : uri,
					type : "post",
// 					dataType : "json",
					headers : {
// 						"Content-type" : "application/json",
						"X-HTTP-Method-Override" : "post",
						"${_csrf.headerName}" : "${_csrf.token}"
					},
					data : { "memberId" : joinForm.memberId.value },
					async : false,
					success : function(response) {
						console.log(response);
						console.log("결과");
					},
					error : function(request, status, error) {
                        console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
                        alert("다시 시도해 주세요.");
                        return false;
                    }
				});
			}

			return status;
		}

		function checkAuthKey() {
			var uri = '<c:url value="/member/checkAuthKey.do" />';
			console.log(authForm.authKey.value);
			return false;

			$.ajax({
				url : uri,
				type : "post",
				data : { "authKey" : authForm.authKey.value },
				async : false
			});
		}
		

// 		function movePrevStep() {
// 			$(authForm).hide();
// 			$(joinForm).show();
// 		}
	</script>

<%@include file="/WEB-INF/include/footer.jsp"%>
