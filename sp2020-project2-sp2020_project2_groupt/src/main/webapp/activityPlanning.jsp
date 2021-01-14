<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="csci310.*"%>
<%@page import="csci310.servlet.search.SearchServlet"%>
<%@page import="csci310.model.*"%>
<%@page import="org.json.*"%>
<%@page import="csci310.LikesConnector"%>
<%@page import="com.google.gson.*"%>
<%@page import="org.json.simple.parser.JSONParser"%>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Activity Planning</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 	<link rel="stylesheet"
		href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
		integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
		crossorigin="anonymous">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> -->
<link rel="stylesheet" type="text/css" href="css/styles.css">

<!-- Just added -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.css">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script
	src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>

<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css">

<script
	src="https://cdn.datatables.net/1.10.20/js/dataTables.bootstrap4.min.js"></script>
<script>
    function unloginAlert(){
        alert("Please sign in to continue!");
        window.location = 'login.jsp';
    }
</script>



<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>

<%
	User user = (User) session.getAttribute("user");
	session.setAttribute("caller", "/activityPlanning.jsp");
	boolean loggedIn = session.getAttribute("user") != null;
	int likes=0;
	if(session.getAttribute("likes")!=null){
	likes= (int) session.getAttribute("likes");
	}
%>

</head>
<body>

	<%
		JSONObject res = (JSONObject) request.getAttribute("json");
	%>


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
			<h1>Activity Planning</h1>
		</div>
	</div>

	<div class="container-fluid">
		<div class="col col-6 col-md-4 "
			style="position: inline-block; float: left;">
			<form name="activity-search" id="vacationForm">
				<div class="form-group ">
					<select id="activityType" name="activityType">
						<!-- hot -->
						<option value="surfing">Surfing</option>
						<option value="jet skiing">Jet Skiing</option>
						<option value="beach volleyball">Beach Volleyball</option>
						<option value="swimming">Swimming</option>
						<option value="sunbathing">Sunbathing</option>
						<!-- temperate -->
						<option value="soccer">Soccer</option>
						<option value="football">Football</option>
						<option value="tennis">Tennis</option>
						<option value="badminton">Badminton</option>
						<option value="hiking">Hiking</option>
						<!-- cold -->
						<option value="hockey">Hockey</option>
						<option value="skiing">Skiing</option>
						<option value="snowboarding">Snowboarding</option>
						<option value="tubing">Tubing</option>
						<option value="build a snowman">Build a Snowman</option>
					</select>
				</div>
				<div class="form-group ">
					<input name="numResults" type="number" class="form-control"
						id="numResults" placeholder="Number of Results"
						required="required"
						oninvalid="this.setCustomValidity('illegal value for input num results.')"
						onchange="this.setCustomValidity('')">
				</div>
				<div class="form-group ">
					<input name="SetDistance" type="text" class="form-control"
						id="SetDistance" placeholder="Set Distance in km" required="required"
						oninvalid="this.setCustomValidity('illegal value for input set distance')"
						onchange="this.setCustomValidity('')">
				</div>
				<div class="form-group col-md-6"
					style="float: left; padding-left: 0;">

					<button type="submit" class="btn btn-secondary"
						style="padding-left: 20px; padding-right: 20px; margin: 0 auto; opacity: 0.9;"
						onclick="sendInfo();">Find My Activity Spot</button>




					<!-- 				<input id="toggle" type="checkbox" checked data-toggle="toggle"
					data-on="°F" data-off="°C" data-style="android" data-onstyle="info">
				<label class="switch"> <span class="slider round"></span>
				</label><br> -->


				</div>

				<div class="form-group col-md-6" style="float: left;">
					<div class="onoffswitch" id="unit-changer">
						<input type="checkbox" name="onoffswitch"
							class="onoffswitch-checkbox" id="myonoffswitch" checked onclick="degreeToggle();">
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
						<th>curr Temp</th>
						<th>Distance</th>
						<th>Favorites</th>
						<th>Like</th>
						<th># Likes</th>
					</tr>
				</thead>

				<tbody id="activityTable">

				</tbody>
				<tfoot>
					<tr>
						<th>City</th>
						<th>Country</th>
						<th>curr Temp</th>
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
		<!--good for small screens:  bottom: 0px!important; padding-bottom: 0px; position: relative; float: left; width: 100%;
 -->

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

		var jres =<%=res%>;
		console.log(jres);
		$(document).ready(function() {
			$('#results').DataTable({
				"bsort" : true,
				"bPaginate" : true
			});
		});

		function sendInfo() {

			var cityForm = document.getElementsByName("activity-search")[0];

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
			cityForm.action = "ActivityPlanningSearch";
			cityForm.submit();
		}

		function myFunction(cityName) {
		
				$.post('/AddLikesServlet', { cityname: cityName, sourcePage: "activityPlanning.jsp"}, function(result) {
				    alert('successfully posted');
				});	}
		

		function populateTable(jsn) {
			var table = document.getElementById('activityTable');
			table.innerHTML = '';

			var data = jsn.results.celsius;
			for (var i = 0; i < data.length; i++) {
				var row = `<tr><td>${data[i].city}</td><td>${data[i].country}</td><td><em class="temp">${Math.round(parseFloat(data[i].currentTemp))}</em><em class="unit">°F</em></td><td>${data[i].distance}<p>km</p></td>`;
				table.innerHTML += row;
				if (data[i].favorite == false) {

					var city = data[i].city;
					var country = data[i].country;
					var favorite = data[i].favorite;

					row += `<td><button type="button" class="btn btn-light fav" onclick="AddRemoveFavorite(\'${city}\',\'${country}\',${favorite});">Add to Favorites</button></td><td><i onclick="myFunction(this,\'${city}\' )" class="fa fa-thumbs-up"></i></td><td><%=likes%></td></tr>`;
				} else {
					row += `<td><button type="button" class="btn btn-light fav" onclick="AddRemoveFavorite(\'${city}\',\'${country}\',${favorite});">Delete from Favorites</button></td><td><i onclick="myFunction(this, \'${city}\')" class="fa fa-thumbs-up"></i></td><td><%=likes%></td></tr>`;
				}
			}
		}
		
	 	function degreeToggle(){
	 		  var checkBox = document.getElementById("myonoffswitch");
	 		  if (checkBox.checked == true){
	 			 var temps = document.getElementsByClassName("temp");
	 			 var units = document.getElementsByClassName("unit");

 	 			for (var i = 0; i < temps.length; i++) 
 	 				 document.getElementById("temp").innerHTML=(temps.item(i)*9/5.+32);
 	 			for (var i = 0; i < units.length; i++) 
 	 		 		 document.getElementById("unit").innerHTML="°F";
	 			  } else {
	 	 	 			for (var i = 0; i < temps.length; i++) 
	 					document.getElementById("temp").innerHTML=((temps.item(i))-32*5/9);
	 	 	 			for (var i = 0; i < units.length; i++) 
	 					document.getElementById("unit").innerHTML="°C";
	 			  }
	 		  
	 		} 
	 		

		if (jres != null && jres.success!=false)
			populateTable(jres);
	</script>
</body>
</html>