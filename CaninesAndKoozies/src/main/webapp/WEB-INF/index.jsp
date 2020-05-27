<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Canines and Koozies</title>
</head>
<body>
<%@ include file="navLoggedIn.jsp" %>
<h1>Canines and Koozies</h1>


<c:choose>
	<c:when test ="${! empty user}">
		<h3> Hello ${user.username}</h3>
		
		<form action = "viewAllEvents.do" method = "GET">
			<input type = "submit" value = "Browse Events">
		</form>
		
		<h3>You are attending these events!</h3>
		<c:choose>
		<c:when test = "${! empty events }">
		
			<c:forEach var = "event" items = "${events}" >
				<h3>${event.name}</h3>
				
				<c:choose>
					<c:when test="${! empty event.pictureURL}">
						<a href="searchId.do?id=${event.id}">
							<img src ="${event.pictureURL}">
						</a>
					</c:when>
					<c:otherwise>
						<a href="searchId.do?id=${event.id}">
							<img src = "https://cdn.pixabay.com/photo/2017/03/24/12/58/thirst-2171119_1280.jpg">
						</a>
					</c:otherwise>
				</c:choose>	
				
			</c:forEach>
		
		</c:when>
		
		<c:otherwise>
			<h4>You're not attending any events; try <a href = "viewAllEvents.do">browsing</a> events others have made!</h4>
		</c:otherwise>
	
	
	
	</c:choose>
	<h4>You are hosting these events:</h4>
		<c:choose>
		<c:when test = "${! empty eventsCreated }">
		
			<c:forEach var = "event" items = "${eventsCreated}" >
				<h3>${event.name}</h3>
				
				<c:choose>
					<c:when test="${! empty event.pictureURL}">
						<a href="searchId.do?id=${event.id}">
							<img src ="${event.pictureURL}">
						</a>
					</c:when>
					<c:otherwise>
						<a href="searchId.do?id=${event.id}">
							<img src = "https://cdn.pixabay.com/photo/2017/03/24/12/58/thirst-2171119_1280.jpg">
						</a>
					</c:otherwise>
				</c:choose>	
				
			</c:forEach>
		
		</c:when>
		<c:otherwise>
			<h4>You're not hosting any events yet; try <a href="createEvent.do">making one!</a></h4>
		</c:otherwise>
	</c:choose>
	
	
		<%-- </c:choose> --%>
</c:when> 
	
	<c:otherwise>
	
	<h3>Login:</h3>
	<form action = "login.do" method = "POST">
		<input type = "text" name = "username">
		<input type = "password" name = "password">
		<input type = "submit" value = "Login">
	</form>

	<h3>Register:</h3>
	<form action = "register.do" method = "GET">
		<input type = "submit" value = "Register">
	</form>
	
	<h3>Search:</h3>
	<form action = "search.do" method = "GET">
		<input type = "text" name = "keyword">
		<input type = "submit" value = "Search">
	</form>
	
	<c:choose>
		<c:when test = "${! empty events }">
		
			<c:forEach var = "event" items = "${events}" >
				<h3>${event.name}</h3>
				
				<c:choose>
					<c:when test="${! empty event.pictureURL}">
						<a href="searchId.do?id=${event.id}">
							<img src ="${event.pictureURL}">
						</a>
					</c:when>
					<c:otherwise>
						<a href="searchId.do?id=${event.id}">
							<img src = "https://cdn.pixabay.com/photo/2017/03/24/12/58/thirst-2171119_1280.jpg">
						</a>
					</c:otherwise>
				</c:choose>	
				
			</c:forEach>
		
		</c:when>
		
		<c:otherwise>
			<h1>No events at this time. Try creating one!</h1>
		</c:otherwise>
	
	</c:choose>

	</c:otherwise>
</c:choose> 
<a href = "about.do">About</a>
</body>
</html>