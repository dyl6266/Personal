/**
 * 자료형에 상관없이 값이 비어있는지 체크
 */
function isEmpty(value) {
	if (value == null || value == "" || value == undefined || (value != null && typeof value == "object" && !Object.keys(value).length)) {
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
 * 필드의 값이 비어있으면 메시지를 출력한 다음, 해당 필드로 focus
 */
function checkField(field, fieldName) {
	if (isEmpty($(field).val())) {
		/* alert 메시지 */
		var message = "";
		/* 종성으로 을 / 를 구분 */
		if (charToUnicode(fieldName) > 0) {
			message = fieldName + "을 확인해 주세요.";
		} else {
			message = fieldName + "를 확인해 주세요.";
		}
		alert(message);
		field.focus();
		return false;
	}
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