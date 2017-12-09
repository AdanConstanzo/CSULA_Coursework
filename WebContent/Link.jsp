<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<!-- Latest compiled and minified CSS -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Reddit Clone Link</title>
	</head>
	<body>
		<div class="container">
			<div class="row">
				<div class="col-md-4">
					<form action="AddLink" method="post">
					  <div class="form-group">
					    <label>Title</label>
					    <input type="text" class="form-control" name="title">
					  </div>
					  <div class="form-group">
					    <label>Link</label>
					    <input type="text" class="form-control" name="link">
					  </div>
					  <button type="submit" class="btn btn-default">Submit</button>
					</form>
				</div>
			</div>
			<br>
			<c:forEach items="${links}" var="link">
				<div class="row">
					<div class="col-sm-2 text-center">
						<h2>${link.votes}</h2>
					</div>
					<div class="col-sm-4">
						<p>${link.title }</p>
						<p>${link.link }</p>
						<a href="UpVote?id=${link.id}">Up vote <span class="glyphicon glyphicon-arrow-up" aria-hidden="true"></span> </a> <a href="DownVote?id=${link.id}">Down Vote <span class="glyphicon glyphicon-arrow-down" aria-hidden="true"></span></a>
					</div>
				</div>
			</c:forEach>
		</div> <!--  End of Container -->
	</body>
</html>