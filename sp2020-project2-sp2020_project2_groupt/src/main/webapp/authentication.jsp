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

<style>
</style>
</head>
<body>


	<br>
	<br>

	<div id="login">
		<div class="row">
			<div class="col text-center m-5 title">
				<h1 style="text-align: center;">Enter Passcode</h1>
			</div>
		</div>
		<br>
		<br>
		<br>
		<form action="AuthenticationServlet" method="post">
			<div class="form-row" style="margin: 0 auto;">
				<div class="col-3"></div>
				<div class="col-6">
					<input type="password" class="form-control" name="passcode"
						placeholder="Passcode">
				</div>
				<div class="col-3"></div>
			</div>
			<br>

			<div class="form-row">

				<button type="submit" class="btn btn-secondary"
					style="padding-left: 30px; padding-right: 30px; margin: 0 auto;">Authenticate</button>
			</div>
			<div class="form-row">
				<a href="register.jsp" style="margin: 0 auto;" id="reg">Register</a>

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