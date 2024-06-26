<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <style> 
        table * {margin:5px;}
        table {width:100%;}
    </style>
</head>
<body>
        
    <jsp:include page="../common/menu_bar.jsp" />

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>게시글 상세보기</h2>
            <br>

            <a class="btn btn-secondary" style="float:right;" href="noticeList">목록으로</a>
            <br><br>

            <table id="contentArea" algin="center" class="table">
                <tr>
                    <th width="100">제목</th>
                    <td colspan="3">${notice.noticeTitle }</td>
                </tr>
                <tr>
                    <th>작성자</th>
                    <td>${notice.noticeWriter }</td>
                    <th>작성일</th>
                    <td>${notice.createDate }</td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td colspan="3"></td>
                </tr>
                <tr>
                    <td colspan="4">${notice.noticeContent }</td>
                </tr>
            </table>
            <br>

            <div align="center">
            <c:if test="${sessionScope.loginUser.userId.equels('admin') }">
                <!-- 수정하기, 삭제하기 버튼은 관리자 계정일 경우에만 보여져야 함 -->
                <!-- form 태그의 action 속성 값을 제외한 나머지는 동일 > 어떤 버튼이 눌렸는지만 구분하여 요청 보내기 -->
                <a class="btn btn-primary" onclick="postSubmit(this.innerHTML);">수정하기</a>
               	<a class="btn btn-danger" onclick="postSubmit(this.innerHTML);">삭제하기</a>
                <!-- href응 통해 GET 방식으로 요청 시 URL로 요청 매핑을 조작할 수 있어 보안상 적절치 못함 -->
            </c:if>
                <script>
                
                	function postSubmit(el) {
                		
                	$('#postForm').attr('action', 'noticeUpdate.do').submit();
                	} else {
                		
                		$('#postForm').attr('action', 'noticeDelete.do').submit();
                	}
                	
            		//삼항 연산자 사용
            		
            		/* const attrValue = '수정하기' === el? 'boardUpdate.do' : 'boardDelete.do';
            		
            		$('#postForm').attr('action', attrValue).submit();
                 */
            		}
                </script>
            </div>
            <br><br>

            <!-- 댓글 기능은 나중에 ajax 배우고 나서 구현할 예정! 우선은 화면구현만 해놓음 -->
            <table id="replyArea" class="table" align="center">
                <thead>
                    <tr>
                        <th colspan="2">
                            <textarea class="form-control" name="" id="content" cols="55" rows="2" style="resize:none; width:100%;"></textarea>
                        </th>
                        <th style="vertical-align:middle"><button class="btn btn-secondary">등록하기</button></th> 
                    </tr>
                    <tr>
                        <td colspan="3">댓글(<span id="rcount">3</span>)</td>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th>user02</th>
                        <td>ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ꿀잼</td>
                        <td>2023-03-12</td>
                    </tr>
                    <tr>
                        <th>user01</th>
                        <td>재밌어요</td>
                        <td>2023-03-11</td>
                    </tr>
                    <tr>
                        <th>admin</th>
                        <td>댓글입니다!!</td>
                        <td>2023-03-10</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <br><br>

    </div>
    
    <jsp:include page="../common/footer.jsp" />
    
</body>
</html>