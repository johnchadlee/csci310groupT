var tempUnit = "fahrenheit";
const input = document.getElementById('locationInput');
const formSelector = $('#search-form');
const url = '/api/main-search';
const loc = getLatLong(input);
const dateStr = getCurrentDate();
console.log(dateStr);
const date = getDateFromString(dateStr);
//var info = makeSearch(date, ,)


//function getToday() {
//    let today = new Date();
//    let day = today.getDate();
//    let month = today.getMonth();
//    let year = today.getFullYear();
//    return months[month] + " " + day + ", " + year;
//}
function displayCurrentWeather(current) {
	const city = current.city;
    const country = current.country;
    $('#current-city-val').text(city + ', ' + country);
    $('#current-splitter-val').text('|');
    $('#current-date-val').text(getToday());
    $('#current-icon-val').addClass('wi-forecast-io-' + current.icon);
    $('#current-temp-val').text(current.temp + '\xB0' + tempUnit.toUpperCase().charAt(0));
    $('#current-desc-val').text(current.desc);
}
//function getCurrentWeather(locationId) {
//    const endpoint = 'http://localhost:8080/api/main-search/data?location-id=' + locationId + '&unit=' + tempUnit;
//    $.getJSON(endpoint, function(results) {
//        console.log(results);
//        displayCurrentWeather(results);
//    });
//}

function searchLocation(formSelector, url){
	formSelector.on('submit', function(){
		$.get({
			url: url,
			data: $('#search-form').serialize(),
			dataType: 'JSON',
			success: (response) => {
				console.log(response);
				if(response.success){
					locationsC = response.results.celsius
					locationsF = response.results.farenheit
					let locations = locationsC;
					if (tempUnits == 'F'){
						locations = locationsF;
					}
					getCurrentWeahter(locations);
				}
			}
		});
	});
}