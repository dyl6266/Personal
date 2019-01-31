<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/include/header.jsp"%>

	<h2>board view</h2>
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
					<td>${board.title }</td>
	
					<th>작성자</th>
					<td>${board.writer }</td>
				</tr>
				<tr>
					<th>게시글 유형</th>
					<td>
						<input type="checkbox" <c:if test="${board.noticeYn eq 'Y' }">checked</c:if> disabled />
						<label for="noticeYn">공지글</label>
						&emsp;
						<input type="checkbox" <c:if test="${board.secretYn eq 'Y' }">checked</c:if> disabled />
						<label for="secretYn">비밀글</label>
					</td>
				</tr>
				<tr>
					<th>내용</th>
					<td colspan="3" class="view_text">
						<textarea rows="10" cols="100" readonly>${board.content }</textarea>
					</td>
				</tr>
				<tr>
					<th>첨부 파일</th>
					<td colspan="3">
						<c:choose>
							<c:when test="${empty attachList == false }">
								<c:forEach var="row" items="${attachList }" varStatus="status">
									<div id="fileDiv${status.count }">
										<a href="<c:url value="/common/downAttachFile.do?idx=${row.idx }" />">${row.originalName } (${row.fileSize }KB)</a>
										<a href="javascript:void(0);" onclick="deleteAttachFile(${row.idx}, this);">삭제하기</a><br />
									</div>
								</c:forEach>
							</c:when>
							<c:otherwise>
								첨부된 파일이 없습니다.
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</tbody>
		</table>

		<div>
			<a href="<c:url value="/board/write.do?idx=${board.idx }" />" class="btn">수정하기</a>
			<a href="<c:url value="/board/list.do" />" class="btn">뒤로가기</a>
		</div>

	<script>
		function deleteAttachFile(idx, obj) {
			if (confirm("파일을 정말 삭제하시겠어요?")) {
				var uri = '<c:url value="/common/deleteAttachFile.do" />';

				$.ajax({
					url : uri,
					type : "POST",
					dataType : "json",
					data : { "idx" : idx },
					success : function(response) {
						console.log(response);
						if ( response.MESSAGE == "OK" ) {
							$(obj).parent().remove();
							alert("정상적으로 처리되었습니다.");
							return false;
						} else {
							alert("다시 시도해 주세요.");
							return false;
						}
					},
					error : function(request, status, error) {
						console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
						alert("다시 시도해 주세요.");
						return false;
			        }
				});
			} else {
				return false;
			}
		}
	</script>

<%@include file="/WEB-INF/include/footer.jsp"%>
