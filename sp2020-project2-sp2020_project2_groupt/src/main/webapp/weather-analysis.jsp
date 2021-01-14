<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="java.util.ArrayList"%>
<%@page import="org.json.*"%>
<%@ page import="java.util.List"%>
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

<link rel="stylesheet" type="text/css"
	href="css/bootstrap-4.3.1-dist/css/bootstrap.min.css">
<!-- <script src="js/jquery-3.4.1.min.js"></script>
 -->
<script src="js/weather-analysis/Chart.min.js"></script>

<!-- <link rel="stylesheet" type="text/css"
	href="css/weather-icons.min.css">
<link rel="stylesheet" type="text/css"
	href="css/weather-icons-wind.min.css"> -->

<link rel="stylesheet" type="text/css" href="css/weather-analysis.css">
<script src="js/weather-analysis.js"></script>

<meta charset="UTF-8">
<%@page import="csci310.User"%>
<%
        User user = (User) session.getAttribute("user");
        session.setAttribute("caller", "/weather-analysis.jsp");
        boolean loggedIn = session.getAttribute("user") != null;
    %>
<%
	JSONObject res = (JSONObject) request.getAttribute("json");
%>

    <script>
    function unloginAlert(){
        alert("Please sign in to continue!");
        window.location = 'login.jsp';
    }
</script>

<title>Weather Analysis</title>
</head>
<body>
	<div  name = "useremail">
		<% if (loggedIn) {out.print("Logged in as: " + user.getEmail());} %>
	</div>

        <% if (!loggedIn){
           %><script>unloginAlert();
            </script><%
  // String redirectURL = "https://localhost:8080";
  // response.sendRedirect(redirectURL);
   } %>

	<div class="container-fluid content-container">
		<div class="row page-title">
			<div class="col page-title">
				<h1>Weather Analysis</h1>
			</div>
		</div>
		<div class="row sections">
			<div class="col-lg-3 sections" id="favorites-section">
				<div class="list-title">
					<p>Favorite Cities</p>
				</div>
				<div class="list-group-no-items">
					<p>Currently, there is no city in your favorites list.</p>
				</div>
				<div class="list-group" id="favorite-cities-list"></div>
				<div class="list-buttons">
					<div class="button-container" id="remove-from-favorites-button">
						<button type="button" class="btn remove-fav-button">Remove
							from Favorites</button>
					</div>
					<div class="onoffswitch" id="unit-changer">
						<input type="checkbox" name="onoffswitch"
							class="onoffswitch-checkbox" id="myonoffswitch" checked>
						<label class="onoffswitch-label" for="myonoffswitch"> <span
							class="onoffswitch-inner"></span> <span
							class="onoffswitch-switch"></span>
						</label>
					</div>
				</div>
			</div>
			<div class="col-lg-5 sections" id="weather-section">
				<div class="current">
					<div class="current-row">
						<div class="current-city">
							<p id="current-city-val"></p>
						</div>
						<div class="current-splitter">
							<p id="current-splitter-val"></p>
						</div>
						<div class="current-date">
							<p id="current-date-val"></p>
						</div>
					</div>
					<div class="current-row">
						<div class="current-icon">
							<i class="wi" id="current-icon-val"></i>
						</div>
						<div class="current-temp">
							<h5 id="current-temp-val"></h5>
						</div>
					</div>
					<div class="current-row current-desc">
						<p id="current-desc-val"></p>
					</div>
				</div>
			</div>
			<div class="col-lg-4 sections" id="photo-section"></div>
		</div>
	</div>
  
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
				title="Bootstrap"> <span class="links">Analysis</span></a> 
				
				<% if (!loggedIn){%>
				<a
				class="navbar-brand" href="login.jsp"> <img class="move-center"
				src="img/login_original.png" alt="" width="20" height="20"
				title="Bootstrap"> <span class="links">Login</span></a>
				<% } else {%>
				<!-- should priint out that you have successfully logged out -->
				<a
				
				class="navbar-brand" href="/logout"> <img class="move-center"
				src="img/login_original.png" alt="" width="20" height="20"
				title="Bootstrap"> <span class="links">Logout</span></a>
				<% }%>
				
		</nav>

	<!--
    <div id="dialog-confirm" title="Confirm" data-displayed="false">
        <p id="dialog-remove-confirm-message">Are you sure you want to remove <span id="dialog-selected-city"></span> from favorites?</p>
    </div>
-->
	<div id="dialog-confirm" title="Confirm" displayed="false">
		<div id="dialog-confirm-box">
			<div id="dialog-confirm-box-row-1">
				<p id="dialog-remove-confirm-message">
					Are you sure you want to remove <span id="dialog-selected-city"></span>
					from favorites?
				</p>
			</div>
			<div id="dialog-confirm-box-row-2">
				<button class="dialog-confirm-buttons" id="dialog-yes">Yes</button>
				<button class="dialog-confirm-buttons" id="dialog-cancel">Cancel</button>
			</div>
		</div>
	</div>

	<!-- <script src="css/bootstrap-4.3.1-dist/js/bootstrap.bundle.min.js"></script>
 -->
	<!--
    <link rel="stylesheet" type="text/css" href="css/weather-analysis/jquery-ui-1.12.1.custom/jquery-ui.min.css">
    <script src="css/weather-analysis/jquery-ui-1.12.1.custom/jquery-ui.js"></script>

-->
<script>
<%-- var jres = <%=res%>;
console.log(jres); --%>

function handleCitySelection() {
    $('a').click(function() {
        $('a.list-group-item.active').removeClass('active');
        $(this).addClass('active');

        const locationId = $(this).data('location-id');
        getWeatherData(locationId)
        var query=$(this).data('city');
    });
}



/*
Iterator iterator = list.iterator();
while(iterator.hasNext()) {
   System.out.println(iterator.next());
}

function ImageSlideshow()
{
	
	var images = [], x = 0;

	images[0] = "assets/example-photo.jpg";
	images[1] = "assets/hollywood.jpg";
	images[2] = "assets/downtown.jpeg";

	function changeImage() {
		var img = document.getElementById("changingImg");
		img.src = images[x];
		x++;

		if (x >= images.length) {
			x = 0;
		}

		fadeImg(img, 5000, true);
		setTimeout("changeImage()", 4000);
	}

	function fadeImg(el, val, fade) {
		if (fade === true) {
			val--;
		} else {
			val++;
		}

		if (val > 0 && val < 100) {
			el.style.opacity = val / 100;
			setTimeout(function() {
				fadeImg(el, val, fade);
			}, 10);
		}
	}

	setTimeout("changeImage()", 2000);
	
}
 */



</script>
</body>
</html>
