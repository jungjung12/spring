<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>B강의장 프로젝트</title>
<style>
.innerOuter { height: 900px; }

</style>
</head>
<body>
	<jsp:include page="common/menu_bar.jsp"></jsp:include>
	
	<div class="innerOuter">
		
		<h4>게시글 조회수 TOP5</h4>
		<br>
		<a href="boardList" style="float:right; color: lightgray; font-weight: 800; font-size: 14px">더 보기</a>
		<br><br>
		
		<table class="table table-hover" align="center" id="boardList">
		
			<thead>
				<tr>
					<td>글 번호</td>
					<td>제목</td>
					<td>작성자</td>
					<td>조회수</td>
					<td>작성일</td>
					<td>첨부파일</td>
				</tr>
			</thead>
			<tbody>
				<!-- Board 테이블에서 조회수가 높은 상위 5개 레코드를 조회한 정보 뿌려주기 -->
			</tbody>
		</table>
		<br>
		<table id="board-detail" class="table">
			
		</table>
	</div>
	
	<script>
	
		$(() => {
			//동적인 요소에 이벤트 부여 -> on 메소드 사용  |  이벤트 타깃으로 지정하고 싶은 상위요소 지정 - 주로 document
			// on('이벤트 달기', '이벤트를 주고 싶은 것의 상위 요소 : 상위 요소를 선택해 하위 요소까지 이벤트를 줄 수 있음')
			$(document).on('click', '#boardList > tbody > tr', e => {
				
				const boardNo = $(e.currentTarget).children().eq(0).text();
				
				$.ajax({
					url: 'boards/'+boardNo,
					type : 'get',
					success : result => {
						
						//console.log(result);
						
						let value = '<tr><td colspan="3"> 게시글 상세 보기 </td></tr>';
						
						value += '<tr>'
							  + '<td>' + result.boardTitle + '</td>'
							  + '<td>' + result.boardContent + '</td>'
							  + '<td>' + result.boardWriter + '</td>'
							  + '</tr>';
							  
							  document.getElementById('board-detail').innerHTML = value;
					}
				});
				
			});
		})	
	
		$(() => {
			findToFivaBoard();
		})
	
		function findToFivaBoard() {
			
			$.ajax({
				url : 'boards',
				type : 'get',
				success : boards => {
					
					// console.log(boards);
					
					let value="";
					
					for(let i in boards) {
						
						value += '<tr>'
							   + '<td>' + boards[i].boardNo + '</td>'
							   + '<td>' + boards[i].boardTitle + '</td>'
							   + '<td>' + boards[i].boardWriter + '</td>'
							   + '<td>' + boards[i].count + '</td>'
							   + '<td>' + boards[i].createDate + '</td>'
							   + '<td>';
							   
							   if(boards[i].originName != null) {
								   value += '★';
							   }
							   + '</td></tr>';
					}
					$('#boardList tbody').html(value);
				}
			});
		} 
	
	</script>
	<footer>
		<jsp:include page="common/footer.jsp"></jsp:include>
	</footer>
</body>
</html>