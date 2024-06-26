<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <style>
        .content {
            background-color:rgb(247, 245, 245);
            width:80%;
            margin:auto;
        }
        .innerOuter {
            border:1px solid lightgray;
            width:80%;
            margin:auto;
            padding:5% 10%;
            background-color:white;
        }

        #boardList {text-align:center;}
        #boardList>tbody>tr:hover {cursor:pointer;}

        #pagingArea {width:fit-content; margin:auto;}
        
        #searchForm {
            width:80%;
            margin:auto;
        }
        #searchForm>* {
            float:left;
            margin:5px;
        }
        .select {width:20%;}
        .text {width:53%;}
        .searchBtn {width:20%;}
    </style>
</head>
<body>
    
    <jsp:include page="../common/menu_bar.jsp" />

    <div class="content">
        <br><br>
        <div class="innerOuter" style="padding:5% 10%;">
            <h2>게시판</h2>
            <br>
            <!-- 관리자 계정일 경우만 보여지는 글쓰기 버튼 -->
            <c:if test="${loginUser.userId.equals('admin') }">
            	<a class="btn btn-secondary" style="float:right;" href="noticeForm">글쓰기</a>
            </c:if>
            <br>
            <br>
            <table id="boardList" class="table table-hover" align="center">
                <thead>
                    <tr>
                        <th>글번호</th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>작성일</th>
                    </tr>
                </thead>
                <tbody>
                
                <c:choose>
                	<c:when test="${list.size() == 0 }">
                		<tr>
                			<td colspan="6">게시글이 존재하지 않습니다.</td>
                		</tr>
                	</c:when>
                	<c:otherwise>
	                	<c:forEach items="${ list }" var="notice">
	                		<tr>
		                 		<td>${notice.noticeNo }</td>
		                 		<td>${notice.noticeTitle }</td>
		                 		<td>${notice.noticeWriter }</td>
		                 		<td>${notice.createDate }</td>
		                 	</tr>
	                	</c:forEach>
                	</c:otherwise>
                	</c:choose>
                </tbody>
            </table>
            <br>

            <div id="pagingArea">
                <ul class="pagination">
                    <li class="page-item disabled">
                    <a class="page-link" href="#">이전</a>
                    </li>
                    
                   	<c:forEach begin="${pageInfo.startPage }" end="${pageInfo.endPage }" var="p">
                   	
	                   <c:choose>
	                    	<c:when test="${ empty condition }">
		                   		<li class="page-item">
		                   			<a class="page-link" href="noticeList?page=${p }" >${p }</a>
		                   		</li>
	                   		</c:when>
	                   		<c:otherwise>
	                   			<li class="page-item">
		                   			<a class="page-link" href="search.do?page=${p }&condition=${condition}&keyword=${keyword}" >${p }</a>
		                   		</li>
	                   		</c:otherwise>
	                   </c:choose>
	                   
					</c:forEach>                   
                   <c:choose>
                   	<c:when test="${pageInfo.maxPage eq pageInfo.currentPage }">
	                    <li class="page-item">
	                    	<a class="page-link" href="#">다음</a>
	                    </li>
                    </c:when>
                    <c:otherwise>
                    	<li class="page-item">
	                    	<a class="page-link" href="boardList?page=${ pageInfo.currentPage + 1 }">다음</a>
	                    </li>
                    </c:otherwise>
                    </c:choose>
                </ul>
            </div>

            <br clear="both"><br>

            <form id="" action="" method="get" align="center">
                <div class="select">
                    <select class="custom-select" name="condition">
                        <option value="writer">작성자</option>
                        <option value="title">제목</option>
                        <option value="content">내용</option>
                    </select>
                </div>
                <div class="text">
                    <input type="text" class="form-control" name="keyword" val="${keyword }">
                </div>
                <button type="submit" class="searchBtn btn btn-secondary">검색</button>
            </form>
            <br><br>
            <!-- 선택한 옵션이 검색 후에도 유지도록 함 -->
            <script>
            $(() => {
            
            	$('#searchForm option[value="${condition}"]').attr('selected', true);
            });
            </script>
        </div>
        <br><br>

    </div>

    <jsp:include page="../common/footer.jsp" />

</body>
</html>