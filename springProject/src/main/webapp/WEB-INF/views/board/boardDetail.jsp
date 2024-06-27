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

            <a class="btn btn-secondary" style="float:right;" href="boardList">목록으로</a>
            <br><br>

            <table id="contentArea" algin="center" class="table">
                <tr>
                    <th width="100">제목</th>
                    <td colspan="3">${board.boardTitle }</td>
                </tr>
                <tr>
                    <th>작성자</th>
                    <td>${board.boardWriter }</td>
                    <th>작성일</th>
                    <td>${board.createDate }</td>
                </tr>
                <tr>
                    <th>첨부파일</th>
                    <c:choose>
                    	<c:when test="${empty board.originName }">
		                    <td colspan="3">
		                        파일이 존재하지 않습니다.
		                    </td>
                    	</c:when>
                    	<c:otherwise>
                    		<td colspan="3">
		                        <a href="${board.changeName }" download="${board.originName }">${board.originName }</a>
		                    </td>
                    	</c:otherwise>
                    </c:choose>
                </tr>
                <tr>
                    <th>내용</th>
                    <td colspan="3"></td>
                </tr>
                <tr>
                    <td colspan="4">${board.boardContent }</td>
                </tr>
            </table>
            <br>

            <div align="center">
            <c:if test="${sessionScope.loginUser.userId eq requestScope.board.boardWriter }">
                <!-- 수정하기, 삭제하기 버튼은 이 글이 본인이 작성한 글일 경우에만 보여져야 함 -->
                <!-- form 태그의 action 속성 값을 제외한 나머지는 동일 > 어떤 버튼이 눌렸는지만 구분하여 요청 보내기 -->
                <a class="btn btn-primary" onclick="postSubmit(this.innerHTML);">수정하기</a>
               	<a class="btn btn-danger" onclick="postSubmit(this.innerHTML);">삭제하기</a>
                <!-- href응 통해 GET 방식으로 요청 시 URL로 요청 매핑을 조작할 수 있어 보안상 적절치 못함 -->
            </c:if>
            	<form action="" method="post" id="postForm">
					<input type="hidden" name="boardNo" value="${board.boardNo }" />
					<input type="hidden" name="filePath" value="${board.changeName }" />
                </form>
                
                <script>
                
                	function postSubmit(el) {
                		
                	/*	$('#postForm').attr('action', 'boardUpdate.do').submit();
                	} else {
                		
                		$('#postForm').attr('action', 'boardDelete.do').submit();
                	} */
                	
            		//삼항 연산자 사용
            		
            		const attrValue = '수정하기' === el? 'boardUpdate.do' : 'boardDelete.do';
            		
            		$('#postForm').attr('action', attrValue).submit();
                
            		}
                </script>
            </div>
            <br><br>

            <!-- 댓글 기능은 나중에 ajax 배우고 나서 구현할 예정! 우선은 화면구현만 해놓음 -->
            <table id="replyArea" class="table" align="center">
                <thead>
                	<c:choose>
                		<c:when test="${empty sessionScope.loginUser }">
		                    <tr>
		                        <th colspan="2">
		                            <textarea class="form-control" cols="55" rows="2" style="resize:none; width:100%;" >로그인 후 이용 가능합니다.</textarea>
		                        </th>
		                        <th style="vertical-align:middle"><button class="btn btn-secondary">등록하기</button></th> 
		                    </tr>
                    	</c:when>
                    	<c:otherwise>
                    		<tr>
		                        <th colspan="2">
		                            <textarea class="form-control" id="content" cols="55" rows="2" style="resize:none; width:100%;" ></textarea>
		                        </th>
		                        <th style="vertical-align:middle"><button onclick="saveReply();" class="btn btn-secondary">등록하기</button></th> 
		                    </tr>
                    	</c:otherwise>
                    </c:choose>
                    <tr>
                        <td colspan="3">댓글(<span id="rcount">0</span>)</td>
                    </tr>
                </thead>
                <tbody>
                <!-- 
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
				 -->                    
                </tbody>
            </table>
        </div>
        <br><br>

    </div>
    <script>
    
    function saveReply() {
    	
    	if($('#content').val().trim() != '') {
    		
    		$.ajax({
    			url : 'reply',
    			data : {
    				refBoardNo : ${board.boardNo},
    				replyContent : $('#content').val(),
    				//문자열로 인식할 수 있게 따옴표 붙여주기~!
    				replyWriter : '${sessionScope.loginUser.userId}'
    			},
    			type : 'post',
    			success : result => {
    				
    				console.log(result);
    				
    				if(result == 'success') {
    					
    					selectReply();
    					$('#content').val('');
    				}
    			}
    		});
    	}
    	else {
    		
    		alertify.alrert('댓글을 입력하삼');
    		
    	}
    	
    	
    	
    }
    
    
    $(document).ready(() => {
    	selectReply();
    });
    
    // 여러번 사용해야 하기 때문에 함수로 빼둠
    function selectReply() {
    	
    	$.ajax({
    		url : 'reply',
    		type : 'get',
    		data : {
    			boardNo : ${board.boardNo}
    		},
    		success : result => {
    			// console.log(result);
    			
    			let resultStr = '';
    			
    			for(let i in result) {
    				resultStr += '<tr>'
    						  + '<td>' + result[i].replyWriter + '</td>'
    						  + '<td>' + result[i].replyContent + '</td>'
    						  + '<td>' + result[i].createDate + '</td>'
    						  + '</tr>';
    			}
    			//id가 replyAread인 테이블의 자식 요소인 tbody를 선택
    			$('#replyArea tbody').html(resultStr);
    			$('#rcount').text(result.length);
    		}
    	});
    	
    } 
    
    </script>
    <jsp:include page="../common/footer.jsp" />
    
</body>
</html>