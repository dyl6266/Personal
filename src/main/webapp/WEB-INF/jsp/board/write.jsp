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
				<tr>
					<th>첨부 파일</th>
					<td colspan="3">
						<div id="filesDiv">
							<c:choose>
								<c:when test="${type eq 'update' && empty attachList == false }">
									<c:forEach var="row" items="${attachList }" varStatus="status">
											<span>
												<a href="javascript:void(0);">${row.originalName } (${row.fileSize }KB)</a>
												<a href="javascript:void(0);" onclick="deleteAttachFile(${row.idx }, this);">&emsp;삭제하기</a><br />
											</span>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<span>
										<input type="file" name="attachFile" /><br /><br />
									</span>
								</c:otherwise>
							</c:choose>
						</div>
					</td>
				</tr>
			</tbody>
		</table>

		<input type="submit" class="btn" value="등록하기" />
		<input type="button" class="btn" value="파일추가" onclick="addAttachFile(this);" />
		<a href="<c:url value="/board/list.do" />" class="btn">뒤로가기</a>
	</form>

	<script>
		function checkForm(obj) {

			/* 공지글, 비밀글 설정 */
			if ( obj.noticeYn.checked ) {
				obj.noticeYn.value = 'Y';
			} else {
				obj.noticeYn.value = 'N';
			}
			
			if ( obj.secretYn.checked ) {
				obj.secretYn.value = 'Y';
			} else {
				obj.secretYn.value = 'N';
			}

			return (
					checkField(obj.title, "제목")
				 && checkField(obj.writer, "작성자")
				 && checkField(obj.content, "내용")
			);
		}

		/**
		 * 첨부 파일 추가
		 */
		var fileCnt = 1; // 파일 개수 카운트용 전역 변수

		function addAttachFile(obj) {
			var filesDiv = $(obj).parent().find("div");
			var html = "";
			html += '<span>';
				html += '<input type="file" name="attachFile'+(fileCnt++)+'" />';
				html += '<a href="javascript:void(0);" onclick="removeInput(this);">삭제하기</a><br />';
			html += '</span>';

			filesDiv.append(html);
		}

		/**
		 *
		 */
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

		function removeInput(obj) {
			$(obj).parent().remove();
		}
	</script>

<%@include file="/WEB-INF/include/footer.jsp"%>
