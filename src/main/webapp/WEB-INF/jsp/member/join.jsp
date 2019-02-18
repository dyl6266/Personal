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
								<input type="text" name="frontAddr" class="type2" value="" maxlength="15" />
								<span>@</span>
								<input type="text" name="backAddr" class="type2" readonly />
								<select onchange="changeDomain(this)">
									<option value="">도메인 선택</option>
									<option value="naver.com">naver.com</option>
									<option value="hanmail.net">hanmail.net</option>
									<option value="nate.com">nate.com</option>
									<option value="gmail.com">gmail.com</option>
									<option value="daum">daum.net</option>
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
							<td><input type="password" name="memberPw" value="" maxlength="20" placeholder="영문자, 숫자, 특수문자 조합 8~20자리로 입력해 주세요." /></td>
						</tr>
						<tr>
							<th><b>*</b> 비밀번호 재입력</th>
							<td><input type="password" name="memberPwCheck" value="" maxlength="20" placeholder="비밀번호를 다시 입력해 주세요." /></td>
						</tr>
						<tr>
							<th><b>*</b> 이름</th>
							<td><input type="text" name="memberName" class="type2" value="" placeholder="이름을 입력해 주세요." /></td>
						</tr>
						<tr>
							<th><b>*</b> 연락처</th>
							<td><input type="text" name="memberPhone" value="" placeholder="'ㅡ' 없이 숫자만 입력해 주세요." /></td>
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

				<div class="btn_wrap text_right">
					<input type="submit" value="회원가입" class="btn btn_jade" />
					<a href="<c:url value="/index.do" />" class="btn btn_gray ml05">취소</a>
				</div>
			</div>
			<!-- // 콘텐츠 끝 -->
		</form>

	</div>
	<!-- // contents -->

	<script>
		/**
		 * 도메인(이메일) 변경
		 */
		function changeDomain(obj) {
			var form = document.joinForm;

			/* 직접 입력 */
			if (obj.value == "direct") {
				form.backAddr.value = "";
				form.backAddr.removeAttribute("readonly");
				form.backAddr.focus();
			} else {
				form.backAddr.value = obj.value;
				form.backAddr.setAttribute("readonly", "true");
			}
		}

		function checkForm(obj) {
			obj.memberId.value = obj.frontAddr.value + "@" + obj.backAddr.value;
			return true;
		}
	</script>

<%@include file="/WEB-INF/include/footer.jsp"%>
