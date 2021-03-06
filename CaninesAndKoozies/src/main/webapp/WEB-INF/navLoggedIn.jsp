<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<nav class="navbar sticky-top navbar-toggleable-md "
	style="background-color: #6B7A8F;">
	<div>

		<div class="float-left">
			<a class="nav-link" href="home.do">
				<button type="button" class="btn btn-outline-light">Home</button>
			</a>
		</div>
		
		<c:choose>
		
		<c:when test = "${!empty user}">
		</c:when>
			<c:otherwise>
			<div class="float-left">
			<a class="nav-link" href="register.do">
			<button type="button" class="btn btn-outline-light">Register</button>
			</a>
			</div>
			</c:otherwise>
		</c:choose>
		
		
		
		
		<c:choose>
		
		<c:when test = "${!empty user}">
		
		<!-- FOR WHEN LOGGED IN -->
		<div>
			<ul class="navbar-nav">
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" role="button"
					id="navbarDropdownMenuLink" data-toggle="dropdown"
					aria-haspopup="true" aria-expanded="false">
						<button type="button" class="btn btn-outline-light">${user.username }</button>
				</a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
						<a class="dropdown-item btn btn-primary" href="viewYourProfile.do">View Profile</a>
							<a class="dropdown-item btn btn-primary" href="userUpdatedPage.do">Update Profile</a> 
						<a class="dropdown-item btn btn-primary" href="createEvent.do">Create Event</a> 
						<a class="dropdown-item btn btn-outline-warning" href="logout.do">Logout</a>
      					

					</div></li>
			</ul>

		</div>
		</c:when>
			<c:otherwise>
			
			<div>
			<ul class="navbar-nav">
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" role="button"
					id="navbarDropdownMenuLink" data-toggle="dropdown"
					aria-haspopup="true" aria-expanded="false">
						<button type="button" class="btn btn-outline-light">Login</button>
				</a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
					<form action="login.do" method="POST">
					Username: <input type="text" name="username" size="18" required> <br>
					Password: <input type="password" name="password" size="18" required> <br>
					<input type="submit" value="Login">
					</form>
					</div></li>
			</ul>
			</div>
						
			</c:otherwise>
		</c:choose>

	</div>

	<div>
		<form class="form-inline" action = "search.do" method = "GET">
			<input class="form-control mr-sm-2" type="search" name="keyword"
				placeholder="Search" aria-label="Search">
			<button class="btn btn-outline-warning" type="submit">Search</button>
		</form>
	</div>

</nav>


