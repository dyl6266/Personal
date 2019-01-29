<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/include/header.jsp"%>

	<h2>board write</h2>

	<form name="writeForm" id="writeForm" action="<c:url value="/board/process.do" />?type=register" method="post" enctype="multipart/form-data" onsubmit="return checkForm(this)">
		<input type="hidden" name="idx" id="idx" value="${board.idx }" />

		<table class="board_view">
			<colgroup>
				<col width="15%" />
				<col width="35%" />
				<col width="15%" />
				<col width="35%" />
			</colgroup>
			<tbody>
				<c:if test="${type eq 'update' }">
					<tr>
						<th>작성일</th>
						<td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.insertTime }" /></td>
	
						<th>조회수</th>
						<td>${board.viewCnt }</td>
					</tr>
				</c:if>
				<tr>
					<th>제목</th>
					<td><input type="text" name="title" id="title" value="${board.title }" class="wdp_90" /></td>
	
					<th>작성자</th>
					<td><input type="text" name="writer" id="writer" value="${board.writer }" class="wdp_90" /></td>
				</tr>
				<tr>
					<th>게시글 유형</th>
					<td>
						<input type="checkbox" name="noticeYn" id="noticeYn" value="${board.noticeYn }" <c:if test="${board.noticeYn eq 'Y' }">checked</c:if> />
						<label for="noticeYn">공지글</label>
						&emsp;
						<input type="checkbox" name="secretYn" id="secretYn" value="${board.secretYn }" <c:if test="${board.secretYn eq 'Y' }">checked</c:if> />
						<label for="secretYn">비밀글</label>
					</td>
				</tr>
				<tr>
					<th>내용</th>
					<td colspan="4" class="view_text">
						<textarea name="content" id="content" rows="10" cols="100">${board.content }</textarea>
					</td>
				</tr>
			</tbody>
		</table>
		<input type="file" name="file" />

		<input type="submit" class="btn" value="등록하기" />
		<a href="<c:url value="/board/list.do" />" class="btn">뒤로가기</a>
	</form>

	<script>
		function checkForm(obj) {

			/* 공지글, 비밀글 설정 */
			if ( obj.noticeYn.checked == true ) {
				obj.noticeYn.value = 'Y';
			} else {
				obj.noticeYn.value = 'N';
			}
			
			if ( obj.secretYn.checked == true ) {
				obj.secretYn.value = 'Y';
			} else {
				obj.secretYn.value = 'N';
			}

			return (checkField(obj.title, "제목")
				 && checkField(obj.writer, "작성자")
				 && checkField(obj.content, "내용"));
		}

// 		function register(type) {
// 			var idx = "${idx }";
// 			var title = $( "#title" );
// 			var content = $( "#content" );
// 			var writer = $( "#writer" );
// 			var noticeYn = $( "#noticeYn" );
// 			var secretYn = $( "#secretYn" );

// 			/* 공지글, 비밀글 설정 */
// 			noticeYn.val( nvl($("#noticeYn:checked").val(), 'N') );
// 			secretYn.val( nvl($("#secretYn:checked").val(), 'N') );

// 			var uri = '<c:url value="/board/process.do?type=register" />';
// 			var params = {
// 				"type" : type,
// // 				"idx" : idx,
// 				"title" : title.val(),
// 				"content" : content.val(),
// 				"writer" : writer.val(),
// 				"noticeYn" : noticeYn.val(),
// 				"secretYn" : secretYn.val()
// 			};

// 			$.ajax({
// 				url : uri,
// 				type : "post",
// 				enctype : "multipart/form-data",
// // 				dataType : "json",
// 				headers : {
// 					"Content-Type" : "application/json",
// 					"X-HTTP-Method-Override" : "post"
// 				},
// 				data : JSON.stringify(params),
// 				success : function(response) {
// 					if ( !isEmpty(response) && response.MESSAGE == "OK" ) {
// 						alert("게시글이 정상적으로 등록되었습니다.");
// 					} else {
// 						alert("알 수 없는 오류가 발생했습니다. 다시 시도해 주세요.");
// 					}

// 					location.href = '<c:url value="/board/list.do" />';
// 				}
// 			});
// 		}
	</script>

<%@include file="/WEB-INF/include/footer.jsp"%>
