<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<title>Ajax</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

</head>

<body>

<input type="text" id="userId"><span id="result" ></span>

<script>

$("#id").on("click",() => {

var id= $("#id").val();

var regex = let regExp = /^[a-zA-Z0-9]{5,12}$/;;

if(!regex.test(id)){

$("#result").html("id형식이 맞지 않습니다. ");

return;

}

else {

$.ajax({

url:"idCheck",

type : "get"

data:{id: $("#id").text()},

error:resp => {

$("#result").html(resp);

}

})

})

</script>

</body>

</html>