<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="bg" class="models.BGBean" scope="session" />
<jsp:setProperty name="bg" property="*" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>BGColor</title>
</head>

<jsp:setProperty property="r" name="bg" param="red" />
<jsp:setProperty property="g" name="bg" param="green" />
<jsp:setProperty property="b" name="bg" param="blue" />



<body style="background: rgb(<jsp:getProperty name="bg" property="r" />, <jsp:getProperty name="bg" property="g" />, <jsp:getProperty name="bg" property="b" />);">
	
	
<p>Change background color using request parameters: r, g, and b.</p>

</body>
</html>