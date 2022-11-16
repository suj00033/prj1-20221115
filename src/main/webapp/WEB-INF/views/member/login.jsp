<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>

	<form action="" method="post">
		id <input type="text" name="username"> <br>
		pw <input type="text" name="password"> <br>
		
		<%-- <sec:csrfInput/> 원래 해야하지만 너무 복잡해서 이 과정은 생략 --%>
		<input type="submit" value="로그인">
	</form>
</body>
</html>