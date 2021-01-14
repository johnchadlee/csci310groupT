<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="csci310.*"%>
<%@page import="csci310.servlet.search.SearchServlet"%>
<%@page import="csci310.model.*"%>
<%@page import="org.json.simple.*"%>
<%@page import="com.google.gson.*"%>
<%@page import="org.json.simple.parser.JSONParser"%>
<%@page import="java.lang.Math"%>
<%@page import="java.lang.Float"%>

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

<%
        User user = (User) session.getAttribute("user");
        session.setAttribute("caller", "/main-search.jsp");
        boolean loggedIn = session.getAttribute("user") != null;
    %>

<style>
.btn-info {
	background-color: gray !important;
	border-color: gray !important;
}
#tablearea{
   overflow-y:scroll;
   height:100px;
   display:block;
}
</style>
</head>
<body>
	<div>
		<% if (loggedIn) {out.print("Logged in as: " + user.getEmail() + "<div><a href= '/logout'>Log out</a></div>");} %>
	</div>



	<%
	/* WeatherInfo weatherInfo = (WeatherInfo) session.getAttribute("weatherInfo");
	String noResultsFound = (String) session.getAttribute("noResults");
	*/
	
	String json = (String)request.getAttribute("json");
	JSONObject weatherInfo=null;
	String description=null;
	WeatherAPIConnector wac=new WeatherAPIConnector();
	if(json!=null){
    JSONParser parser = new JSONParser();
    weatherInfo= (JSONObject) parser.parse(json);
    System.out.println(weatherInfo);
    description=String.valueOf(weatherInfo.get("desc"));
	}
	%>
	<br>
	<div id="animation" width="100%;">
		<svg version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg"
			xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
			viewBox="0 0 60.7 44.4" style="max-height: 100px;"
			xml:space="preserve">
 <%=weatherInfo != null ? WeatherAPIConnector.weatherAnimations(description) : "<br><br><br><br><br><br><br><br><br><br><br>"%>
 </svg>

		<%--   <%=weatherInfo != null ? weatherInfo.get("icon") : ""%>
 --%>
	</div>
	<% if(weatherInfo != null) {%>
	<div class="weather-display">
		<p id="location"><%=weatherInfo.get("location") %></p>
		<br>
		<p id="date"><%=WeatherAPIConnector.getCurrentDate()%></p>
		<p id="temp"><%=weatherInfo.get("temp")%>°F
		</p>
		<p id="weather-desc"><%= weatherInfo.get("desc") %>
		</p>
	</div>
	<% } else if (weatherInfo==null) { %>
	<!-- 		<div class="weather-display">
			<h2> No weather data found. </h2>
		</div> -->

	<% } %>
	<br>
	<br>

	<div id="mainSearch">
		<!-- action="SearchServlet" method = "POST"  -->
		<form name="main-search"
			class="form-inline d-flex justify-content-center md-form form-sm mt-0">
			<input class="form-control" type="text"
				placeholder="Enter location (city or zip)" name="location"
				id="homepage-search">
		</form>
		<div id="controls">
			<button type="button" class="btn btn-secondary btn-sm"
				onclick="sendInfo();">Show me the Weather</button>
				<br></br>
				<div id="tablearea"></div>

			<!-- <input id="toggle" type="checkbox" checked data-toggle="toggle" data-on= "°F" data-off="°C" data-style="android" data-onstyle="info" onchange="toggleDegrees()">	
 	 	<label class="switch">
     	<span class="slider round"></span>
     	</label><br> -->

		</div>


	</div>


	<nav class="navbar fixed-bottom navbar-light" style="bottom: 0px;">
		<a class="navbar-brand" href="main-search.jsp"> <img
			src="img/house-fill.svg" alt="" width="20" height="20"
			title="Bootstrap"> <span>Home</span>
		</a> <a class="navbar-brand" href="vacationPlanning.jsp"> <img
			class="move-center" src="img/umbrella.svg" alt="" width="20"
			height="20" title="Bootstrap"> <span>Vacation</span></a> <a
			class="navbar-brand" href="activityPlanning.jsp"> <img
			src="img/soccer.svg" alt="" width="20" height="20" title="Bootstrap">
			<span>Activity</span></a> <a class="navbar-brand"
			href="weather-analysis.jsp"> <img class="move-center"
			src="img/calculator.svg" alt="" width="20" height="20"
			title="Bootstrap"> <span>Analysis</span></a> <a
			class="navbar-brand" href="login.jsp"> <img class="move-center"
			src="img/login_original.png" alt="" width="20" height="20"
			title="Bootstrap"> <span>Login</span></a>
	</nav>


</body>
<script>
var requestUrl = "/getSearchServlet";
var xhttp = new XMLHttpRequest();
xhttp.onreadystatechange = function() {
	if (this.readyState == 4 && this.status == 200) {
    	var response = this.responseText;
    	console.log(response);
    	updateUI(response);
    }
	else if(this.status == 400){
		displayError();
	}
};

xhttp.open("GET", requestUrl, true);
xhttp.send();

var mainSearches = [];
var mainSearchesDegrees = [];

function updateUI(response){
	var result = response.split(";");
	for (var i = 0; i < result.length-1; i++){
		mainSearches.push(result[i]);
	}
	if(mainSearches.length > 0){
		mainSearches.reverse();
	tableCreate();
	}
}

//mainSearches = ["San Francisco", "Los Angeles", "New York"];
//mainSearchesDegrees = ["Fareinheit", "Celsius", "Fareinheit"];

function tableCreate() {
	  var body = document.getElementById('tablearea');
	  var tbl = document.createElement('table');
	  tbl.className = "table table-striped table-bordered";
	  var tra = document.createElement('tr');   
	    var td1a = document.createElement('td');
	   // var td2 = document.createElement('td');
	    var text1a = document.createTextNode("Recent Searches");
	    //var text2 = document.createTextNode(mainSearchesDegrees[i]);
	    td1a.appendChild(text1a);
	    td1a.style.fontWeight="bold";
	   // td2.appendChild(text2);
	    tra.appendChild(td1a);
	    tbl.appendChild(tra);
	  for (var i = 0; i < mainSearches.length; i++) {
		var tr = document.createElement('tr');   
	    var td1 = document.createElement('td');
	   // var td2 = document.createElement('td');
	    var text1 = document.createTextNode(mainSearches[i]);
	    //var text2 = document.createTextNode(mainSearchesDegrees[i]);
	    td1.appendChild(text1);
	    td1.id = i;
	    td1.onclick = function(){
	    	alert(parseInt(td1.id));
	    	document.getElementById("homepage-search").value = mainSearches[parseInt(td1.id)];
	    	sendInfo();
	    }
	   // td2.appendChild(text2);
	    tr.appendChild(td1);
	    tbl.appendChild(tr);
	  }
	  body.appendChild(tbl)
}
</script>
<script>
  console.log("just testing");
	function sendInfo(){
/* 		var cityZipName = document.getElementsByName("location")[0].value;//zip code
 */		
		//get an invisible form idk why don't ask
		var cityForm = document.getElementsByName("main-search")[0];
		//get input for city name
		var inputCity = document.getElementsByName("location")[0];
	    var enteredLocation = document.getElementById("homepage-search");
	    var enteredUnit = "Fareinheit";
	/*	var requestUrl = "/addSearchServlet?username=abhayakr&password=abby&search=" + enteredLocation + "@" + enteredUnit;
		alert(requestUrl);
    	var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if(this.status == 400){
				displayError();
			}
			else{
				alert("here");
			}
		}
		xhttp.open("GET", requestUrl, true);
		xhttp.send();*/
  			//send in post method
  		cityForm.method = "POST";
			//to weatherAPIConnector
  		cityForm.action = "SearchServlet";   
		cityForm.submit();
	}  
	
<%-- 	function toggleDegrees() {
		console.log("toggle button pressed");
		if(degrees == 0) {
			degrees = 1;
			document.getElementById("toggle").innerHTML="°C";
			<%if(weatherInfo != null) { %>
				document.getElementById("temp").innerHTML = "<%=weatherInfo.getCelsius()%>";
			<%}%>
		} else {
			degrees =0;
			document.getElementById("toggle").innerHTML="°F";
			<%if(weatherInfo != null) { %>
			this.avgMinCelsiusText = Integer.toString( (int) (avgMin - 273.15)) + "°C";
				document.getElementById("temp").innerHTML = "<%=

%>";
			<%}%>
		}
	} --%>
	
/* 	$(document).keypress(
		  function(event){
		    if (event.which == '13') {
		      event.preventDefault();
		    }
	});
	 */
</script>
</html>
