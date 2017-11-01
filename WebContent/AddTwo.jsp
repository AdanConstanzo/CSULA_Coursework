<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
String a = request.getParameter("a");
String b = request.getParameter("b");

if( a == null || a.trim().length() == 0 || b == null || b.trim().length() == 0 )
{
    response.sendRedirect( "AddForm.html" );
    return;
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>
</head>
<body>
The sum of <%= a %> and <%= b %> is
<%= Integer.parseInt(a) + Integer.parseInt(b)  %>.
</body>
</html>