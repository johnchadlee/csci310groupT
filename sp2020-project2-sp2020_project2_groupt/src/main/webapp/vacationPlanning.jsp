<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="csci310.servlet.search.SearchServlet"%>
<%@page import="csci310.model.*"%>
<%@page import="org.json.*"%>
<%@page import="com.google.gson.*"%>
<%@page import="org.json.simple.parser.JSONParser"%>

<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Vacation Planning</title>

<link rel="stylesheet" type="text/css" href="css/styles.css">

<!-- Just added -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.css">

<script
  src="https://code.jquery.com/jquery-3.5.0.min.js"
  integrity="sha256-xNzN2a4ltkB44Mc/Jz3pT4iU1cmeR0FkXs4pru/JxaQ="
  crossorigin="anonymous"></script>

<!-- <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
 --><script
	src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
<script
	src="https://cdn.datatables.net/1.10.20/js/dataTables.bootstrap.min.js"></script>
<script>
    function unloginAlert(){
        alert("Please sign in to continue!");
        window.location = 'login.jsp';
    }
</script>

<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css">

<script
	src="https://cdn.datatables.net/1.10.20/js/dataTables.bootstrap4.min.js"></script>




<%@page import="csci310.*"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%
	User user = (User) session.getAttribute("user");
	session.setAttribute("caller", "/vacationPlanning.jsp");
	boolean loggedIn = session.getAttribute("user") != null;
%>
<%
	JSONObject res = (JSONObject) request.getAttribute("json");
int likes=0;
if(session.getAttribute("likes")!=null){
likes= (int) session.getAttribute("likes");
}
%>


</head>
<body>

	<div>
		<%
			if (loggedIn) {
				out.print("Logged in as: " + user.getEmail());
			}
		%>

	</div>
        <% if (!loggedIn){
           %><script>unloginAlert();
            </script><%
  // String redirectURL = "https://localhost:8080";
  // response.sendRedirect(redirectURL);
   } %>
	<div class="row">
		<div class="col text-center m-5 title" style="margin-bottom: 3%;">
			<h1>Vacation Planning</h1>
		</div>
	</div>
	<div class="container-fluid">
		<div class="col col-6 col-md-4 "
			style="position: inline-block; float: left;">

			<form id="vacationForm" name="vacation-search">
				<div class="form-group ">
					<select id="precipType" name="precipType">
						<option value="clear">Clear</option>
						<option value="cloudy">Cloudy</option>
						<option value="rain">Rain</option>
						<option value="snow">Snow</option>
						<option value="sleet">Sleet</option>
						<option value="wind">Wind</option>
						<option value="fog">Fog</option>
					</select>
				</div>
				<!-- "tempRangeLow", "tempRangeHigh", "numResults", "currLocation", "precipType"}; -->
				<div class="form-group col-md-6"
					style="float: left; margin-left: 0; padding-left: 0;">

					<input type="number" name="tempRangeLow" required="required"
						class="form-control" placeholder="Low °F"
						oninvalid="this.setCustomValidity('illegal value for input min temp.')"
						onchange="this.setCustomValidity('')">
				</div>
				<div class="form-group col-md-6"
					style="float: left; padding-right: 0; padding-left: 0;">
					<input type="number" name="tempRangeHigh" required="required"
						class="form-control" placeholder="High °F"
						oninvalid="this.setCustomValidity('illegal value for input max temp.')"
						onchange="this.setCustomValidity('')">
				</div>


				<div class="form-group">
					<input type="number" name="numResults" class="form-control"
						id="numResults" required="required"
						placeholder="Number of Results"
						oninvalid="this.setCustomValidity('illegal value for input num results.')"
						onchange="this.setCustomValidity('')">
				</div>
				<div class="form-group ">
					<input name="SetDistance" type="text" class="form-control"
						id="SetDistance" placeholder="Set Distance" required="required"
						oninvalid="this.setCustomValidity('illegal value for input set distance')"
						onchange="this.setCustomValidity('')">
				</div>
				<div class="form-group col-md-6"
					style="float: left; padding-left: 0;">
					<button type="submit" class="btn btn-secondary"
						style="padding-left: 10px; padding-right: 10px; margin: 0 auto; opacity: 0.9;"
						onclick="sendInfo();">Find My Vacation Spot</button>
				</div>
				<div class="form-group col-md-6" style="float: left;">
					<div class="onoffswitch" id="unit-changer">
						<input type="checkbox" name="onoffswitch"
							class="onoffswitch-checkbox" id="myonoffswitch" checked>
						<label class="onoffswitch-label" for="myonoffswitch"> <span
							class="onoffswitch-inner"></span> <span
							class="onoffswitch-switch"></span>
						</label>
					</div>

				</div>

			</form>
		</div>


		<%
			if (res != null) {
		%>
		<div class="col col-md-6"
			style="margin-right: 0; position: inline-block; float: left; margin-bottom: 10%;">

			<table class="table table-striped table-bordered" id="results">
				<thead>
					<tr>
						<th>City</th>
						<th>Country</th>
						<th>Avg Min</th>
						<th>Avg Max</th>
						<th>Distance</th>
						<th>Favorites</th>
						<th>Like</th>
						<th># Likes</th>
					</tr>
				</thead>

				<tbody id="vacationTable">

				</tbody>
				<tfoot>
					<tr>
						<th>City</th>
						<th>Country</th>
						<th>Avg Min</th>
						<th>Avg Max</th>
						<th>Distance</th>
						<th>Favorites</th>
						<th>Like</th>
						<th># Likes</th>
					</tr>
				</tfoot>
			</table>


		</div>

		<%
			} else {
			}
		%>



		<nav class="navbar fixed-bottom navbar-light" style="bottom: 0px;">
			<a class="navbar-brand" href="main-search.jsp"> <img
				src="img/house-fill.svg" alt="" width="20" height="20"
				title="Bootstrap"> <span class="links">Home</span>
			</a> <a class="navbar-brand" href="vacationPlanning.jsp"> <img
				class="move-center" src="img/umbrella.svg" alt="" width="20"
				height="20" title="Bootstrap"> <span class="links">Vacation</span></a>
			<a class="navbar-brand" href="activityPlanning.jsp"> <img
				src="img/soccer.svg" alt="" width="20" height="20" title="Bootstrap">
				<span class="links">Activity</span></a> <a class="navbar-brand"
				href="weather-analysis.jsp"> <img class="move-center"
				src="img/calculator.svg" alt="" width="20" height="20"
				title="Bootstrap"> <span class="links">Analysis</span></a>

			<%
				if (!loggedIn) {
			%>
			<a class="navbar-brand" href="login.jsp"> <img
				class="move-center" src="img/login_original.png" alt="" width="20"
				height="20" title="Bootstrap"> <span class="links">Login</span></a>
			<%
				} else {
			%>
			<!-- should priint out that you have successfully logged out -->
			<a class="navbar-brand" href="/logout"> <img class="move-center"
				src="img/login_original.png" alt="" width="20" height="20"
				title="Bootstrap"> <span class="links">Logout</span></a>
			<%
				}
			%>

		</nav>

	</div>

	<script>
		//console logs latitude longitude of curr location...we can use this instead of current location text box

		navigator.geolocation.getCurrentPosition(success, error);
		var latitude = 34.0946955;
		var longitude = -118.28738750000001;
		function success(position) {
			latitude = position.coords.latitude;
			longitude = position.coords.longitude;
			console.log(position.coords.latitude)
			console.log(position.coords.longitude)

		}

		function error(err) {
			console.warn(`ERROR(${err.code}): ${err.message}`);
		}

		var jres =
	<%=res%>
		;

		$(document).ready(function() {
			$('#results').DataTable();
		});

		function sendInfo() {

			var cityForm = document.getElementsByName("vacation-search")[0];

			  var x = document.createElement("input");
			  x.setAttribute("type", "number");
			  x.setAttribute("step", "any");
			  x.setAttribute("name", "latitude");
			  x.setAttribute("value", latitude);
			  var y = document.createElement("input");
			  y.setAttribute("type", "number");
			  y.setAttribute("step", "any");
			  y.setAttribute("name", "longitude");
			  y.setAttribute("value", longitude);
			  x.style.display="none";
			  y.style.display="none";
			cityForm.appendChild(x);
			cityForm.appendChild(y);

			cityForm.method = "POST";
			cityForm.action = "VacationPlanningSearch";
			cityForm.submit();
		}
		
		var degrees = 0;

		//tried this but didn't work
		function toggleDegrees() {
			console.log("toggle button pressed");
			if (degrees == 0) {
				degrees = 1;
				document.getElementById("toggle").innerHTML = "°C";
				if (jres != null) {

					var temps = document.getElementsByClassName("mintemp");

					for (var i = 0; i < temps.length; i++) {
						temps[i].innerHTML = (32 * temps[i].value - 32) * 5 / 9;
					}

				}
			} else {//fahrenheit
				degrees = 0;
				document.getElementById("toggle").innerHTML = "°F";
				if (jres != null) {
					var temps = document.getElementsByClassName("maxtemp");

					for (var i = 0; i < temps.length; i++) {
						temps[i].innerHTML = (temps[i].value * 9 / 5) + 32;
					}
				}
			}
		}

		//there is a bug one of the servlets with nullptr exception
		//was trying to resolve but got a bit stuck
		//you'll see when you try to add favorites after you've put onclick()  back in the td tag inside populateTable
		
		
		function myFunction(cityName) {
			
			$.post('/AddLikesServlet', { cityname: cityName, sourcePage: "vacationPlanning.jsp"}, function(result) {
			    alert('successfully posted');
			});	}



		
		
		function populateTable(jsn) {
			var table = document.getElementById('vacationTable');
			table.innerHTML = '';
			var data = jsn.results.celsius;
			for (var i = 0; i < data.length; i++) {
				var row = `<tr><td>${data[i].city}</td><td>${data[i].country}</td><td><em class="tempMin">${Math.round(parseFloat(data[i].avgMinTemp))}</em><em class="unit">°F</em></td><td><em class="tempMax">${Math.round(parseFloat(data[i].avgMaxTemp))}</em><em class="unit">°F</em></td><td>${data[i].distance}<p>km</p></td>`;
				if (data[i].favorite == false) {

					var city = data[i].city;
					var country = data[i].country;
					var favorite = data[i].favorite;
					console.log(country);
					row += `<td><button type="button" class="btn btn-light fav" onclick="AddRemoveFavorite(\'${city}\',\'${country}\',${favorite});">Add to Favorites</button></td><td><i onclick="myFunction(this, \'${city}\')" class="fa fa-thumbs-up"></i></td><td><%=likes%></td></tr>`;
				} else {
					row += `<td><button type="button" class="btn btn-light fav" onclick="AddRemoveFavorite(\'${city}\',\'${country}\',${favorite});">Remove from Favorites</button></td><td><i onclick="myFunction(this, \'${city}\')" class="fa fa-thumbs-up"></i></td><td><%=likes%></td></tr>`;
				}

				table.innerHTML += row;
			}
		}

		if (jres != null) {
			populateTable(jres);
		}
	</script>
</body>
</html>