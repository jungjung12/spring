<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항이당당</title>
<style>
	#content {
		width : 800px;
		height : 1200px;
		margin : auto;
	}

	#outerDiv{
		width : 800px;
		height : 400px;
		padding-top : 50px;
	}

	.noticeEl {
		width: 100%;
		height : 60px;
		margin: auto;
		line-height: 60px;
		text-align: center;
	}

	.noticeEl > div {
		display: inline-block;
	}

	#title{
		margin-top : 100px;
		text-align: center;
	}

	#detail{
		background-color:#23C293; 
		width:800px; 
		margin: auto;
		text-align:center;
		color : white;
		height : 150px;
		display: none;
	}
	
	#detail > div{
		height : 50px;
		line-height: 50px;
		border : 1px solid rgba(255, 255, 255, 0.656);
	}

</style>
</head>
<body>

	<jsp:include page="../common/menu_bar.jsp"/>
	
	<div id="content">
		<h1 id="title">공지사항 게시판입니다!</h1>
		<button class="btn btn-danger btn-sm" data-toggle="modal" href="#noticeModal">글작성</button>

	</div>	
	
	<div id="detail">
		
	</div>
	
	<jsp:include page="../common/footer.jsp"/>

	<div class="modal fade" id="noticeModal">
		<div class="modal-dialog">
		  <div class="modal-content">
	
			<div class="card">
			  <div class="card-header text-white" style="background-color: #ff52a0;">게시글 작성하기</div>
			  <div class="card-body">       
				
				  <div class="form-group">
					<label>작성자</label>
					<input type="text" class="form-control" id='noticeWriter' value="${sessionScope.loginUser.userId }" readonly>
				  </div>
				  
				  <div class="form-group">
					<label>제목</label>
					<input type="text" class="form-control" id='noticeTitle' value="">
				  </div>
		
				  <div class="form-group">
					<label>내용</label>
					<textarea class="form-control" rows="5" id='noticeContent' style="resize: none;"></textarea>
				  </div>
				 
				  <a class="btn" data-dismiss="modal"
			  style="background-color: #ff52a0; height: 40px; color: white; border: 0px solid #388E3C; opacity: 0.8">닫기</a>&nbsp;&nbsp;
					  <a class="btn" onclick="insert();"
				  style="background-color: red; height: 40px; color: white; border: 0px solid #388E3C; opacity: 0.8">작성하기</a>&nbsp;&nbsp;
				   
			  </div>
			</div>
		  </div>
		</div>
	</div>

	<div class="modal fade" id="updateModal">
		<div class="modal-dialog">
		  <div class="modal-content">
	
			<div class="card">
			  <div class="card-header text-white" style="background-color: #ff52a0;">게시글 수정하기</div>
			  <div class="card-body">       
				
					<input type="hidden" value="" id="updateNo"/>
				  <div class="form-group">
					<label>작성자</label>
					<input type="text" class="form-control" id='updateWriter' value="" readonly>
				  </div>
				  
				  <div class="form-group">
					<label>제목</label>
					<input type="text" class="form-control" id='updateTitle' value="">
				  </div>
		
				  <div class="form-group">
					<label>내용</label>
					<textarea class="form-control" rows="5" id='updateContent' style="resize: none;"></textarea>
				  </div>
				 
				  <a class="btn" data-dismiss="modal"
			  style="background-color: #ff52a0; height: 40px; color: white; border: 0px solid #388E3C; opacity: 0.8">닫기</a>&nbsp;&nbsp;
					  <a class="btn" onclick="update();"
				  style="background-color: orange; height: 40px; color: white; border: 0px solid #388E3C; opacity: 0.8">수정하기</a>&nbsp;&nbsp;
				   
			  </div>
			</div>
		  </div>
		</div>
	</div>
	<script>
	/*
		자바스크립트 선호 표기법. 극적인 효과는 없음..
		
		funcion() {} | () =>
		funcion(a) {} | (a) =>
		funcion(a,b) {} | (a,b) =>
		funcion() { return a } | () => a
	*/
		window.onload = () => {
			
			findAll();
		}
		
		// 공지사항 추가
		function insert() {
			
			const requestData = {
					"noticeTitle" : $('#noticeTitle').val(),
					"noticeContent" : $('#noticeContent').val(),
					"noticeWriter" : $('#noticeWriter').val()
			};
			
			$.ajax({
				url : 'notice',
				type : 'post',
				data: requestData,
				success : response => {
					
					// console.log(requestData);
					
					// 컨트롤러에서 반환 결과가 서비스 요청 성공이면 -> content 영역에 재조회 해서 띄우기
					if(response.message === '서비스 요청 성공') {
						$('#outerDiv').remove();
						findAll();
						$('#noticeTitle').val('');
						$('#noticeContent').val('');
					}
				}
			});
		}
		
		
		// 삭제하기
		
		function deleteById(noticeNo) {
			
			$.ajax({
				url : 'notice/' + noticeNo,
				type : 'delete',
				success : response => {
					
					//consol.log(response);
					if(response.data === '삭제 성공') {
						$('detail').slideUp(300);
						$('outerDiv').remove();
						findAll();
					};
				}
			});
		}
		
		
		// 상세보기
		$('#content').on('click', '.noticeEl', e => {
			
			const noticeNo = $(e.currentTarget).children().eq(0).text();
			
			$.ajax({
				url : 'notice/' + noticeNo,
				type : 'get',
				success : response => {
					// console.log(response);
					
					const notice = response.data;
					
					// console.log(notice);
					
					const contentValue = '<div id="notice-detail">'
										+ '<div>' + notice.noticeTitlte + '</div>'
										+ '<div>' + notice.noticeContent + '</div>'
										+ '<div>'
										+ '<a class="btn btn-sm btn-warning" data-toggle="model" href="#updateModal">'
										+ '수정하기'
										+ '</a> | '
										//요소 만드는 시점에 매개변수를 받아 전송할 수 있게 함
										+ '<a class="btn btn-sm btn-secondary" onclick="deleteById('+ notice.noticeNo +')">삭제하기</a>'
										+ '</div>'
										+ '</div>';
										
				
				$('detail').html(contentValue);
				$('detail').slideDown(500);
				}
				
			});
			
		});
		
		// 목록 조회
		const findAll = () => {
			
			$.ajax({
				url : 'notice',
				type : 'get',
				success : response => {
					// console.log(response);
					
					const noticeList = response.data;
					
					//console.log(noticeList);
					//제일 큰 틀이 되는 div 요소
					const outerDiv = document.createElement('div');
					//id 속성 추가
					outerDiv.id = 'outerDiv';
					//console.log(outerDiv);
					
					noticeList.map(o => {
						
						// console.log(o);
						
						const noticeEl = document.createElement('div');
						//class 속성 추가
						noticeEl.className = 'noticeEl';
					
						//noticeNo가 들어갈 div 요소 및 자식 요소 설정
							/*	
						const numEl = document.createElement('div');
						
					
						const numText = document.createTextNode(o.noticeNo);
						numEl.style.width = '50px';
						// console.log(numEl);
						// console.log(numText);
						numEl.appendChild(numText);
						// console.log(numEl);
						//numEl을 noticeEl의 자식 요소로 만들기
						noticeEl.appendChild(numEl);
						
						
						//noticeTitle이 들어갈 div 요소 및 자식 요소 설정
						const titleEl = document.createElement('div');
						const titleText = document.createTextNode(o.noticeTitle);
						titleEl.style.width = '400px';
						titleEl.appendChild(titleText);
						noticeEl.appendChild(titleEl);
						
						//noticeWriter가 들어갈 div 요소 및 자식 요소 설정
						const writerEl = document.createElement('div');
						const writerText = document.createTextNode(o.noticeWriter);
						writerEl.style.width = '150px';
						writerEl.appendChild(writerText);
						noticeEl.appendChild(writerEl);
						
						//createDate가 들어갈 div 요소 및 자식 요소 설정
						const dateEl = document.createElement('div');
						const dateText = document.createTextNode(o.createDate);
						dateEl.style.width = '200px';
						dateEl.appendChild(dateText);
						noticeE.appendChild(dateEl);
						*/
						
							noticeEl.appendChild(createDiv(o.noticeNo, '50px'));
							noticeEl.appendChild(createDiv(o.noticeTitle, '400px'));
							noticeEl.appendChild(createDiv(o.noticeWriter, '150px'));
							noticeEl.appendChild(createDiv(o.createDate, '200px'));
							
							outerDiv.appendChild(noticeEl);
					});
					
					document.getElementById('content').appendChild(outerDiv);
				}
			});
		}
		
		function createDiv(data, style){
	          const divEl = document.createElement('div');
	          const divText = document.createTextNode(data);
	          divEl.style.width = style;
	          divEl.appendChild(divText);
	          return divEl;
	      }
		
		
	</script>
	

</body>
</html>