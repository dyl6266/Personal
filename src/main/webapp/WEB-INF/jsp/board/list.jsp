<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/include/header.jsp"%>

	<h2>board list</h2>
	
	<table class="board_list">
		<colgroup>
			<col width="7%" />
			<col width="*" />
			<col width="10%" />
			<col width="18%" />
			<col width="5%" />
		</colgroup>
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
				<th>조회수</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${!empty boardList }">
					<c:forEach var="row" items="${boardList }" varStatus="status">
						<tr>
							<td>${row.idx }</td>
							<td class="title">
								<a href="<c:url value="/board/view.do?idx=${row.idx }" />">${row.title }</a>
							</td>
							<td>${row.writer }</td>
							<td>
								<fmt:formatDate pattern="yyyy-MM-dd" value="${row.insertTime }" />
							</td>
							<td>${row.viewCnt }</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="5">검색된 게시글이 없습니다.</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>

	<a href="<c:url value="/board/write.do?type=insert" />" class="btn">글쓰기</a>
	<a href="javascript:void(0);" class="btn" onclick="test();">테스트</a>
	
<%@include file="/WEB-INF/include/footer.jsp"%>
