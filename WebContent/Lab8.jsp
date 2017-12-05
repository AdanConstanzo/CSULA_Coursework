<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="cs3220" uri="http://cs3.calstatela.edu/cs3220stu56/examples" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	
	<form method="post" action="Lab8.jsp">
    Insert Number <br />
    <input type="text" name="op1" />
    <input type="submit" value="Done" />
    </form>  
    <!-- removed tag from here -->
	
	<cs3220:add op1="${param['op1']}"/>
	<br>
</body>
</html>