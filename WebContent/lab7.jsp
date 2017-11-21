<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>JSTL SQL Query</title>
</head>
<body>


<%-- set data source --%>
<sql:setDataSource
	driver="com.mysql.jdbc.Driver"
	url="jdbc:mysql://cs3.calstatela.edu/cs3220stu56"
	user="cs3220stu56"
	password="x0V4ihSd"/>

<c:catch var ="catchException">
   <c:if test="${not empty param.input}">
		<sql:query var = "items">
			${param.input}
		</sql:query>
	</c:if>
</c:catch>


<div class="container">

<div class="page-header">
	<h3>SQL Query</h3>	
</div>

<div class="well">
	<form action="lab7.jsp" method="post">
		
		<c:if test = "${catchException != null}">
		   <p class="lead text-danger">Invalid Query: <code>${param.input}</code> </p>
		</c:if>
		<div class="form-group">
			<textarea class="form-control" rows="5"  name="input" value="${param.input}"></textarea>
		</div>
		<div class="text-center">
			<input class="btn btn-primary" type="submit" name="submit" value="Execute Query">
		</div>
	</form>
	<c:if test="${not empty items}">
		<p>
			<strong>${items.getRowCount()} record(s)</strong> returned for: <code>${param.input}</code>
		</p>
		<table class="table table-striped table-bordered table-hover">
			<tr>
				<c:forEach items="${items.columnNames}" var = "name">
					<th>${name }</th>
				</c:forEach>
			</tr>
			<c:forEach items="${items.rowsByIndex}" var="row">
				<tr>
					<c:forEach items="${row}" var="col">
						<td>${col}</td>
					</c:forEach>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</div>

</div>
</body>
</html>

