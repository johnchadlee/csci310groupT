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

</head>
<body>


	<br>
	<br>


	<div id="login">
		<div class="row">
			<div class="col text-center m-5 title">
				<h1 style="text-align: center;">Register</h1>
			</div>
		</div>
		<br> <br> <br>
		<form>
			<div class="form-row col-3"
				style="margin: 0 auto; margin-bottom: 1%;">

				<input type="email" class="form-control" id="exampleInputEmail1"
					aria-describedby="emailHelp" placeholder="Enter email">
			</div>

			<div class="form-row col-3"
				style="margin: 0 auto; margin-bottom: 1%;">
				<input type="password" class="form-control"
					id="exampleInputPassword1" placeholder="Password">
			</div>

			<div class="form-row col-3"
				style="margin: 0 auto; margin-bottom: 1%;">
				<input type="password" class="form-control"
					id="exampleInputPassword1" placeholder="Retype Password">
			</div>


			<div class="form-row">

				<button type="submit" class="btn btn-secondary"
					style="padding-left: 30px; padding-right: 30px; margin: 0 auto;">Register</button>

			</div>

			<div class="form-row">
				<a href="login.jsp" style="margin: 0 auto;" id="log">Login</a>

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
				class="navbar-brand" href="login.jsp"> <img class="move-center"
				src="img/login_original.png" alt="" width="20" height="20"
				title="Bootstrap"> <span class="links">Login</span></a>
		</nav>

</body>

</html>