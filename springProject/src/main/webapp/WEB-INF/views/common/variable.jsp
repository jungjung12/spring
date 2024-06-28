<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>var, let, const</h1>
	
	<h2>변수 선언 시 사용할 수 있는 키워드들</h2>
	
	<button onclick="defDeclare();">버튼임</button>
	
	<script>
		function defDeclare() {
			
			// var : 중복 선언 가능 + 값을 재할당 가능
			var userId = 'user01';
		
			var userId = 'user02';
			
			userId = 'user03';
			
			//var를 보완한 let : 값을 재할당 가능 + 중복선언 불가
			
			let userName = '이가을';
			
			let userName = '이정희';
			
			//const(상수) : 선언과 동시에 값을 대입하여 초기화를 진행해야만 사용 가능 + 중복 선언, 값을 재할당 불가능
			const userPwd;
			userPwd = 123;
			
			//let, const 위주로 사용하는 것을 권장
			//변수 선언 시 const를 사용, for문 처럼 값이 바뀔 때는 let으로 사용
		}
		
	</script>
	
	<h1 id="title">요소</h1>
	
	<button onclick="keywordScope();">버튼임</button>
	
	<script>
		function keywordScope() {
			
			//let, const : 사용이 된 블럭 내에서만 사용 가능
			if(1) {
				let gender = 'M';
				const hobby = '취미';
				
			}
			//var : function이 선언 된 함수영역 내에서 사용 가능
			for(var i=0; i<5;i++) {
				
			}
		}	
	
	</script>
	
</body>
</html>