/**
 * 자료형에 상관없이 값이 비어있는지 체크
 */
function isEmpty(obj) {
	if (obj == null || obj == "" || obj == undefined || (obj != null && typeof obj == "object" && !Object.keys(obj).length)) {
		return true;
	} else {
		return false;
	}
}

/**
 * 문자열의 마지막 문자의 종성 반환
 */
function charToUnicode(str) {
	return (str.charCodeAt(str.length - 1) - 0xAC00) % 28;
}

/**
 * 필드1, 필드2의 값이 다르면 해당 필드2로 focus한 다음, 메시지 출력
 */
function checkEquals(field1, field2, fieldName) {
	if ( field1.value === field2.value ) {
		return true;
	} else {
		/* alert 메시지 */
		var message = "";
		/* 종성으로 을 / 를 구분 */
		if (charToUnicode(fieldName) > 0) {
			message = fieldName + "이 일치하지 않습니다.";
		} else {
			message = fieldName + "가 일치하지 않습니다.";
		}
		field2.focus();
		Swal.fire(message);
		return false;
	}
}

/**
 * field의 값이 올바른 형식인지 체크 (정규표현식 사용)
 * 
 * @param field - 타겟 필드
 * @param fieldName - 필드 이름 (null 허용)
 * @param focusField - 포커스할 필드 (null 허용)
 * @param type - 유형 구분 (이메일, 전화번호 등 / null 허용)
 * @returns
 */
function checkValidation(field, fieldName, focusField, type) {
	/* type에 해당하는 정규식 */
	var regExp = "";
	/* alert 메시지 */
	var message = "";

	/* 일반 필드의 경우 */
	if ( isEmpty(type) ) {
		if ( isEmpty(field.value) ) {
			/* 종성으로 을 / 를 구분 */
			if (charToUnicode(fieldName) > 0) {
				message = fieldName + "을 확인해 주세요.";
			} else {
				message = fieldName + "를 확인해 주세요.";
			}
			field.focus();
			Swal.fire(message);
			return false;
		}
	} else {
		/* 타입을 지정해야 하는 경우 */
		switch (type) {
		case "email":
			regExp = /^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,20}$/;
			message = "올바르지 않은 형식의 이메일입니다.";
			break;

		case "password":
			regExp = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{6,20}/;
			message = "올바르지 않은 형식의 비밀번호입니다.";
			break;

		case "phone":
			regExp = /^\d{3}\d{3,4}\d{4}$/;
			message = "올바르지 않은 형식의 연락처입니다.";
			break;

		default:
			break;
		}
		// end of switch

		if ( regExp.test(field.value) == false ) {
			if ( isEmpty($(focusField)) ) {
				field.focus();
			} else {
				focusField.focus();
			}
			Swal.fire(message);
			return false;
		}
	}
	// end of else

	return true;
}

/**
 * value1의 값이 비어있으면 value2를, 그렇지 않으면 value1을 반환
 */
function nvl(value1, value2) {
	if (isEmpty(value1)) {
		return value2;
	} else {
		return value1;
	}
}

/**
 * 다음 주소 API
 * 예:) 우편번호, 주소, 상세주소 태그의 id를 각각 postcode, address, detailAddress로 설정해야 함
 */
function findPostcode() {
	new daum.Postcode({
		oncomplete : function(data) {
			// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

			// 각 주소의 노출 규칙에 따라 주소를 조합한다.
			// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
			var addr = ''; // 주소 변수
			var extraAddr = ''; // 참고항목 변수

			// 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
			if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
				addr = data.roadAddress;
			} else { // 사용자가 지번 주소를 선택했을 경우(J)
				addr = data.jibunAddress;
			}

			// 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
			if (data.userSelectedType === 'R') {
				// 법정동명이 있을 경우 추가한다. (법정리는 제외)
				// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
				if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
					extraAddr += data.bname;
				}
				// 건물명이 있고, 공동주택일 경우 추가한다.
				if (data.buildingName !== '' && data.apartment === 'Y') {
					extraAddr += (extraAddr !== '' ? ', ' + data.buildingName
							: data.buildingName);
				}
				// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
				if (extraAddr !== '') {
					extraAddr = ' (' + extraAddr + ')';
				}
				// 조합된 참고항목을 해당 필드에 넣는다.
				document.getElementById("detailAddress").value = extraAddr;

			} else {
				document.getElementById("detailAddress").value = '';
			}

			// 우편번호와 주소 정보를 해당 필드에 넣는다.
			document.getElementById("postcode").value = data.zonecode;
			document.getElementById("address").value = addr;
			// 커서를 상세주소 필드로 이동한다.
			document.getElementById("detailAddress").removeAttribute("readonly");
			document.getElementById("detailAddress").focus();
		}
	}).open();
}
