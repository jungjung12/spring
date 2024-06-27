<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<meta charset="UTF-8">
<title>Index</title>
</head>
<body>
	<h1>Spring의 비동기 통신 활용법</h1>
	
	<h3>AJAX</h3>
	
	<p>
		Asynchronous JavaScript And XML의 약자 <br>
		서버로부터 데이터를 응답받아 전체 페이지를 다시 만드는 것이 아닌, 일부만 내용물을 바꿀 수 있는 기법<br><br>
		
		참고 - 그동안 사용한 a 태그 혹은 form 태그를 이용한 요청 방식은 동기식 요청 <br>
		 = > HTML 전체를 응답받아 브라우저는 처음부터 끝까지 화면을 랜더링 해야만 결과 확인이 가능했음 <br>
		 
		 비동기 처리를 위해 AJAX를 사용 <br><br>
		 
		 AJAX 구현 방식 : JavaScript / jQuery / fetchAPI / axios 등
		 
		 *동기 / 비동기의 차이
		 	동기식 : 서버가 요청 처리 후 응답 HTML 데이터가 돌아와야만 그 다음 작업이 가능 <br>
		 			만약 서버에서 응답페이지를 돌려주는 시간이 지연되면 그만큼 기다려야 함 <br>
		 			응답 데이터가 돌아오면 전체 화면을 파싱 <br><br>
		 			
			비동기식 : 현재 페이지를 그대로 유지하는 동안 중간중간에 추가 요청을 보낼 수 있음 <br>
					요청을 보낸다고 다른 페이지가 새롭게 랜더링 되는 것이 아닌 현재 페이지가 그대로 유지 <br>
					요청을 보내고 응답이 돌아올 때까지 자른 작업 수행 가능 <br><br>
					
			-> 예약, 중복확인, 검색어 자동 완성 등에 활용. 사실상 SPA는 전부 비동기 처리 <br><br>
			
			비동기식의 단점 <br>
			
			- 요청 후 돌아온 응답 데이터를 가지고 현재 페이지에서 새로운 요소를 동적으로 만들어줘야 함 <br>
			=> DOM 요소를 새롭게 만드는 구문을 잘 익혀둬야 함 <br><br>
			- 페이지 내 복잡도가 기하급수적으로 증가
	</p>
	
	<br><br><br>
	<hr>
	<br><br><br>
	
	<pre>
	
	jQuery 에서의 AJAX 옵션
	
	[ 표현법 ]
	$.ajax({
		속성명 : 속성값,
		속성명 : 속성값,
		속성명 : 속성값,
		
		...
	});
	
		* 주요 속성 :
		- url :  요청할 URL(필수) => form 태그의 action 속성
		- type : 요청 전송 방식(GET/POST/PUT/DELETE) -> 생략 시 기본 값은 get이나 반드시 명시해줘야 함 => form 태그의 method 속성
		- data : 요청 시 전달할 값({키:값, 키:값, ...}) => form 태그의 input 요소의 value 속성
		- success : AJAX 통신 성공 시 콜백 함수를 정의
		
		- error : AJAX 통신 실패 시 콜백 함수를 정의
		- complete : AJAX 통신의 성공 여부에 상관 없이 끝나면 실행할 콜백 함수
		- async : 서버와 비동기 처리 방식 설정 여부(기본값 true)
		- contentType : 요청 시 데이터 인코딩 방식 정의(보내는 측의 데이터 인코딩)
		- dataType : 서버에서 응답 시 돌아오는 데이터의 형태 설정, 미작성 시 스마트하게 판단함
			- xml : 트리 형태
			- json : 맵 형태의 구소(일반적인 데이터 구조)
			- script : 자바스크립트 잋 일반 String형태의 데이터
			- html : html 태그 자체를 리터하는 방식
			- text : String데이터
	</pre>
	
	<h4>AJAX로 요청 보내고 응답 받아오기</h4>
	
	<h5>1. 서버 요청 시 인자값 전달 -> 응답데이터를 받아서 화면에 출력</h5>
	
	<label>종류 : </label>샌드위치, 돈까스, 김치찜, 알밥 <br>
	
	메뉴 : <input type="text" id="menu" />
	수량 : <input type="number" id="amount" /> <br><br>
	
	<button id="btn">서버 전송</button>
	
	<div id="co">
	
	
	</div>
	
	<!-- 버튼 클릭 시 메뉴에 입력한 음식명과 수량에 입력한 값을 가지고 서버에 응답하여 응답받은 데이터를 div 요소의 content 영역에 출력 -->
	
	<script>
	
		document.getElementById('btn').onclick = () => {
			
			const menuValue = document.getElementById('menu').value;
			const amountValue = document.getElementById('amount').value;
			
			$.ajax({
				url : 'ajax1.do',
				type : 'get',
				data : {
					menuValue,
					amountValue
				},
				success : result => {
					// console.log(result);
					
					const resultValue = '선택한 메뉴 ' + menuValue + ' ' + amountValue + '개의 가격 : ' + resultValue + '원'
					document.getElementById('resultMsg').innerHTML = resultValue;
				},
				error : () => {
					console.log('오타일지도');
				}
			});
			
		}
	
	</script>
	
	<h3>DB에서 SELECT를 이용해 조회했다는가정 하에 VO객체를 응답받아서 화면 상에 출력</h3>
	
	조회할 음식 번호 : <input type="number" id="menuNo" /> <br><br>
	
	<button id="select-btn">조회</button>
	
	<div id="menu">
	
	</div>
	
	<script>
	
		window.onclick =()=> {
			//document.getElementById('select-btn') : 이벤트 타깃
			//onclick : 발생시킬 이벤트
			//=()=> : 이벤트 핸들러
			document.getElementById('select-btn').onclick =()=> {
				
				$.ajax({
					// url : 'ajax2.do',
					url : 'ajax3.do',
					type : 'get',
					data : {
						menuNumber : document.getElementById('menuNo').value
					},
					success : result => {
						console.log(result);
						// 자바 스크립트에서 읽을 수 있는 형태로 파싱
						const obj = {
							"menuNumber" : "1",
							"menuName" : "순찌",
							"price" : "9000",
							"material" : "순두부"
						};
						
						console.log(obj);
						
						const menu = '<ul> 오늘의 메뉴 :'
								   + '<li>' + result.manuName + '</li>'
								   + '<li>' + result.price + '</li>'
								   + '<li>' + result.material + '</li>'
								   + '</ul>'
								   
						document.getElementById('menu').innerHTML = menu;
					},
					error : e => {
						console.log(e);
					}
						
				});
				
			};
		}
	</script>
	
	<br>
	<hr>
	<br>
	
	<h3>조회 후 리스트를 응답받아 출력</h3>
	
	<button onclick="findAll()">메뉴 전체 조회</button>
	
	<table boarder="1" id="find-result">
		<thead>
			<tr>
				<th>메뉴명</th>
				<th>가격</th>
				<th>재료</th>
			</tr>
		</thead>
		<tbody id="tbody">
		
		</tbody>
	</table>
	
	<script>
		function findAll() {
			$.ajax({
				url : 'find.do',
				type : 'get',
			
			success : result => {
				console.log(result);
				console.log(result[0]); //객체에 접근
				console.log(result[0].menuName); //객체의 특정 요소에 접근
				
				//이게 정석이라니......
				// JSON 형태로 온 데이터를 출력 요소를 이용해 화면에 띄우는 작업
				
				const tbodyEl = document.createElement('tbody');
				
				result.map((o, i) => {
					
				const trEl = document.createElement('tr');
				// console.log(trEl);
				
				const tdFirst = document.createElement('td');
				const firstText = document.createTextNode(result[0].menuName);
				tdFirst.style.width = '200px';
				tdFirst.appendChild(firstText);
				
				const tdSecond = document.createElement('td');
				const secondText = document.createTextNode(result[0].price);
				tdSecond.style.width = '200px';
				tdSecond.appendChild(secondText);
				
				const tdThird = document.createElement('td');
				const thirdText = document.createTextNode(result[0].amterial);
				tdThird.style.width = '100px';
				tdThird.appendChild(thirdText);
				
				trEl.appendChild(tdFirst);
				trEl.appendChild(tdSecond);
				trEl.appendChild(tdThird);
				
				tbodyEl.appendChild(trEl);
				
				});
				};
			});
		}
	
	</script>
	
	
	
	
	
</body>
</html>