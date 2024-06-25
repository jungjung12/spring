<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
</head>
<body>
    
    <!-- 메뉴바 -->
    <jsp:include page="../common/menu_bar.jsp" />

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>회원가입</h2>
            <br>

            <form action="join.do" method="post">
                <div class="form-group">
                    <label for="userId">* ID : </label>
                    <input type="text" class="form-control" id="userId" placeholder="Please Enter ID" name="userId" required> <br>
                    <div id="checkResult" style="display:none; font-size:0.7rem"></div>

                    <label for="userPwd">* Password : </label>
                    <input type="password" class="form-control" id="userPwd" placeholder="Please Enter Password" name="userPwd" required> <br>

                    <label for="checkPwd">* Password Check : </label>
                    <input type="password" class="form-control" id="checkPwd" placeholder="Please Enter Password" required> <br>

                    <label for="userName">* Name : </label>
                    <input type="text" class="form-control" id="userName" placeholder="Please Enter Name" name="userName" required> <br>

                    <label for="email"> &nbsp; Email : </label>
                    <input type="text" class="form-control" id="email" placeholder="Please Enter Email" name="email"> <br>

                    <label for="age"> &nbsp; Age : </label>
                    <input type="number" class="form-control" id="age" placeholder="Please Enter Age" name="age"> <br>

                    <label for="phone"> &nbsp; Phone : </label>
                    <input type="tel" class="form-control" id="phone" placeholder="Please Enter Phone (-없이)" name="phone"> <br>
                    
                    <label for="address"> &nbsp; Address : </label>
                    <input type="text" class="form-control" id="address" placeholder="Please Enter Address" name="address"> <br>
                    
                    <label for=""> &nbsp; Gender : </label> &nbsp;&nbsp;
                    <input type="radio" id="Male" value="M" name="gender" checked>
                    <label for="Male">남자</label> &nbsp;&nbsp;
                    <input type="radio" id="Female" value="F" name="gender">
                    <label for="Female">여자</label> &nbsp;&nbsp;
                </div> 
                <br>
                <div class="btns" align="center">
                    <button type="submit" id="join-btn" class="btn btn-primary disabled">회원가입</button>
                    <button type="reset" class="btn btn-danger">초기화</button>
                </div>
            </form>
            
            <script>
            	$(() =>{
            		
            		const $idInput = #('.form-group #userId');
            		const $checkResult = #('checkResult');
            		const $joinSubmit = #('join-btn');
            		
            		$idInput.keyup(() => {
            			
            			// console.log($idInput.val().length);
            			
            			//불필요한 DB 접근을 제한하기 위해 다섯글자 이상으로 입력했을 때만 AJAX 요청을 보내 체크
            			
            			if($idInput.val().length >= 5) {
            				
            				$ajax({
            					url : 'idCheck.do',
            					type : 'get',
            					data : {
            						checkId = $idInput.val()
            					},
            					success : response => {
            						
            						// console.log(response)
            						
            				if(response.substr(4)) === 'N') {//중복일 경우
            					$checkResult.show().css('color', 'crimson').text('중복된 아이디 입니다.');
            					$joinSubmit.attr('disabled', true);
            				} else {
            					$checkResult.show().css('color', 'green').text('사용 가능한 아이디 입니다.');
            					$joinSubmit.removeAttr('disabled', false);
            				}
            					},
            					error : ()=> {
            						
            						console.log("실패!")
            					}
            				});
            			}
            			
            		})
            		
            	});
            
            
            </script>
            
        </div>
        <br><br>

    </div>

    <!-- 푸터바 -->
    <jsp:include page="../common/footer.jsp" />

</body>
</html>