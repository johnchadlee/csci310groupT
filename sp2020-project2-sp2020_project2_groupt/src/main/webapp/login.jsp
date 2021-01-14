<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<html>
<head>

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/styles.css">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet"
		href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
		integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
		crossorigin="anonymous">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script>
        function unloginAlert(){
            alert("Please Log in");
    }
    </script>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>


	<br>
	<br>


	<div id="login">
		<div class="row">
			<div class="col text-center m-5 title">
				<h1 style="text-align: center;">Log in</h1>
			</div>
		</div>
		<div style="color: red">
			<p style="text-align: center;">${message}</p>
		</div>
		<br>
		<br>
		<br>
							
					
				<form method="post" action="login">
				  <div class="form-row" style="margin: 0 auto;">
				    <div class="col-3"></div>
				     <div class="col-3">
				    <input type="email" name="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email">
				</div>

				<div class="col-3">
					<input type="password" name="password" class="form-control"
						id="exampleInputPassword1" placeholder="Password">
				</div>
				<div class="col-3"></div>
			</div>
			<br>

			<div class="form-row">
				<button type="submit" class="btn btn-secondary"
					style="padding-left: 30px; padding-right: 30px; margin: 0 auto;">Login</button>
			</div>
			<div class="form-row">
				<a href="register.jsp" style="margin: 0 auto;" id="reg">Register</a>

			</div>
				<div style="color: red">
				    <p id="message" style="text-align: center;">${message}</p>
					</div>
		</form>
		
</div>
	<br>
	<br>

		<nav class="navbar fixed-bottom navbar-light" style="bottom: 0px;">
			<a class="navbar-brand" href="main-search.jsp"> <img
				src="img/house-fill.svg" alt="" width="20" height="20"
				title="Bootstrap"> <span class="links">Home</span>
			</a> <a class="navbar-brand" href="vacationPlanning.jsp"> <img
				class="move-center" src="img/umbrella.svg" alt="" width="20"
				height="20" title="Bootstrap"> <span class="links">Vacation</span></a> <a
				class="navbar-brand" href="activityPlanning.jsp"> <img
				src="img/soccer.svg" alt="" width="20" height="20" title="Bootstrap">
				<span class="links">Activity</span></a> <a class="navbar-brand"
				href="weather-analysis.jsp"> <img class="move-center"
				src="img/calculator.svg" alt="" width="20" height="20"
				title="Bootstrap"> <span class="links">Analysis</span></a> <a
				class="navbar-brand"  href="register.jsp" > <img class="move-center"
				src="img/login_original.png" alt="" width="20" height="20"
				title="Bootstrap"> <span class="links">Register</span></a>
		</nav>

</body>
<%
	session.removeAttribute("message");
%>
</html>