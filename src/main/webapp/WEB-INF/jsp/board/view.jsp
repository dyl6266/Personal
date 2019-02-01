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
				<tr>
					<th>작성일</th>
					<td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.insertTime }" /></td>

					<th>조회수</th>
					<td>${board.viewCnt }</td>
				</tr>
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
			<a href="<c:url value="/board/write.do?type=update&amp;idx=${board.idx }" />" class="btn">수정하기</a>
			<a href="<c:url value="/board/list.do" />" class="btn">뒤로가기</a>
		</div>

<%@include file="/WEB-INF/include/footer.jsp"%>
