<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<!-- Latest compiled and minified CSS -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
		<link rel="stylesheet" href="./css/simpsons.css">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Simpson's Catalog</title>
	</head>
	<body>
		<div class="container">
			
			<div class="jumbotron text-center">
			  <img style="width: 30%;" src="http://albertcervantes.com/cs3220/cdn/simpsons/simpsons.png">
			  <h1>Character Catalog</h1>
			  <p class="lead">Click on an image below to view a random image of your favorite character!</p>
			</div>
			
			<c:forEach items="${characters}" var="character">
				<div class="col-sm-4 text-center">
					<div class="well">
						<a href="CharacterProfile?id=${character.id}">
							<c:choose>
								<c:when test="${character.numberOfPhotos >0}">
									<img style="height: 150px;" src="http://albertcervantes.com/cs3220/cdn/simpsons/${character.directoryName }/pic_0001.jpg" class="img-responsive img-thumbnail" alt="${character.name}">
								</c:when>
								<c:otherwise>
									<img style="height: 150px;" src="http://via.placeholder.com/150?text=N/A" class="img-responsive img-thumbnail" alt="${character.name}">
								</c:otherwise>
							</c:choose>
							<h4 class="text-center">${character.name}</h4>
						</a>
					</div>
				</div>
			</c:forEach>
			
		</div> <!--  End of Container -->
	</body>
</html>