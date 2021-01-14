var tempUnit = "fahrenheit";
$(document).ready(function() {
    loadFavorites();
});

function loadFavorites() {
    const endpoint = "http://localhost:8080/api/favorites/list";
//	const endpoint = "/api/favorites/list/";
    $.getJSON(endpoint, function(results) {
        console.log(results);
        //const favorites = results.favorites;
        const favorites = results;
        if (favorites.length == 0) {
            $('.list-group-no-items').show();
            $('.list-buttons').hide();
            $('.onoffswitch').hide();
        }
        else {
            displayFavorites(favorites);
        }
    });
}
//imageServlet
//will give you links of List<String> format
function changeImages()
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



function displayFavorites(favorites) {
    $('.list-group-no-items').hide();
    $('.list-buttons').show();
    $('.onoffswitch').show();

    const favoritesDiv = $('.list-group');
    for (let location of favorites) {
        let p1 = $('<p>').text(location.city);
        let p2 = $('<p>').text(location.country);
        let div1 = $('<div>').addClass('list-item-city').append(p1);
        let div2 = $('<div>').addClass('list-item-country').append(p2);
        let a = $('<a>')
            .addClass('list-group-item')
            .addClass('list-group-item-action')
            .attr("data-location-id", location.id)
            .attr("city", location.city)
            .attr("country", location.country)
            //            .attr('href', '#')
            .append(div1, div2);
        favoritesDiv.append(a);
    }

    handleCitySelection();
    handleRemoveFromFavoritesButton();
}

function handleCitySelection() {
    $('a').click(function() {
        $('a.list-group-item.active').removeClass('active');
        $(this).addClass('active');

        const locationId = $(this).data('location-id');
        getWeatherData(locationId)
    });
}

function sendInfo(){

	var cityForm = document.getElementsByName("activity-search")[0];
			cityForm.method = "POST";
			cityForm.action = "ActivityPlanningSearch";   
	cityForm.submit();
	
}  

function handleRemoveFromFavoritesButton() {
    $('#remove-from-favorites-button').click(function() {
        if ($('a.list-group-item.active').length) {
            const selectedCity = $('a.list-group-item.active').attr('city');
            $("#dialog-selected-city").text(selectedCity);

            $("#dialog-confirm").attr('displayed', 'true');
            $("#dialog-confirm").css('display', 'flex');
            $("#dialog-yes").click(removeCityFromFavorites);
            $("#dialog-cancel").click(function() {
                $("#dialog-confirm").attr('displayed', 'false');
                $("#dialog-confirm").css('display', 'none');
            });
        }
        else {
            alert("There is no city currently selected.")
        }
    });
}

function removeCityFromFavorites() {
    const endpoint = "http://localhost:8080/api/favorites/remove";
//	const endpoint = "/api/favorites/remove";

    let params = {
        'city' : $('a.list-group-item.active').attr('city'),
        'country' : $('a.list-group-item.active').attr('country')
    };
//    $.post(endpoint, params)
//        .done(function() {

    // remove the city from the list
    $('a.list-group-item.active').remove();

    // remove the weather analysis and photo area
    removeCurrentWeatherArea();
    $('.forecast').remove();
    $('.historic').remove();
    $('#photo-section').empty()

    console.log($('a').length);
    // if it was the last item
    //if ($('a').size() == 0) {
    if ($('a').length == 0) {
        $('.list-group-no-items').show();
        $('.list-buttons').hide();
        $('.onoffswitch').hide();
    }
//        })
//        .fail(function() {
//            alert("There was an error while removing the city from the favorite cities list.");
//        });
    $("#dialog-confirm").attr('displayed', 'false');
    $("#dialog-confirm").css('display', 'none');
}

function removeCurrentWeatherArea() {
    $('#current-city-val').text('');
    $('#current-splitter-val').text('');
    $('#current-date-val').text('');
    $('#current-icon-val').removeClass();
    $('#current-icon-val').addClass('wi');
    $('#current-temp-val').text('');
    $('#current-desc-val').text('');
}

function getWeatherData(locationId) {
    const endpoint = 'http://localhost:8080/api/weather-analysis/data?location-id=' + locationId + '&unit=' + tempUnit;
//	const endpoint = '/api/weather-analysis/data?location-id=' + locationId + '&unit=' + tempUnit;
    $.getJSON(endpoint, function(results) {
        console.log(results);
        displayWeatherData(results);
    });
}

function handleCitySelection() {
    $('a').click(function() {
        $('a.list-group-item.active').removeClass('active');
        $(this).addClass('active');

        const locationId = $(this).data('location-id');
        getWeatherData(locationId)
    });
}


function displayWeatherData(results) {
    displayCurrentWeather(results.current);
    displayForecast(results.forecast);
    displayHistoricData(results.historic);
    displayCityImage(results.images);
}

function displayCurrentWeather(current) {
	
	const city = $('a.list-group-item.active').attr('city');
    const country = $('a.list-group-item.active').attr('country');
    $('#current-city-val').text(city + ', ' + country);
    $('#current-splitter-val').text('|');
    $('#current-date-val').text(getToday());
    $('#current-icon-val').addClass('wi-forecast-io-' + current.icon);
    $('#current-temp-val').text(current.temp + '\xB0' + tempUnit.toUpperCase().charAt(0));
    $('#current-desc-val').text(current.desc);
}

function displayForecast(forecast) {
    if ($('.forecast').length) {
        $('.forecast').remove();
    }
    let parent = $('<div>')
        .addClass('forecast')
        .appendTo('#weather-section');
    let dates = getForecastDates();
    for (let i = 0; i < forecast.length; ++i) {
        let p1 = $('<p>').text(dates[i]);
        let p2 = $('<p>').text(forecast[i].max_temp + '\xB0' + tempUnit.toUpperCase().charAt(0));
        let p3 = $('<p>').text(forecast[i].min_temp + '\xB0' + tempUnit.toUpperCase().charAt(0));
        let i1 = $('<i>').addClass('wi').addClass('wi-forecast-io-' + forecast[i].icon);

        let div1 = $('<div>').addClass('forecast-date').append(p1);
        let div2 = $('<div>').addClass('forecast-icon').append(i1);
        let div3 = $('<div>').addClass('forecast-high').append(p2);
        let div4 = $('<div>').addClass('forecast-low').append(p3);

        let div = $('<div>')
            .addClass('forecast-col')
            .append(div1, div2, div3, div4)
            .appendTo(parent);
    }
}

function displayHistoricData(historic) {
    if ($('.historic').length) {
        $('.historic').remove();
    }
    let parent = $('<div>')
        .addClass('historic')
        .appendTo('#weather-section');
    $('<div>').addClass('col-12')
        .attr('id', 'historic-chart')
        .appendTo('.historic');
    $('<div>').addClass('card')
        .appendTo('#historic-chart');
    $('<div>').addClass('card-body')
        .appendTo('.card');
    $('<canvas>').attr('id', 'chLine')
        .appendTo('.card-body');
    drawChart(historic);
}

function drawChart(historic) {
    const chLine = $("#chLine");
    if (chLine) {
        new Chart(chLine, {
            type: 'line',
            data: {
                labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
                datasets: [{
                    data: historic.highs,
                    backgroundColor: 'transparent',
                    borderColor: '#f57b51',
                    borderWidth: 4,
                    pointBackgroundColor: '#f57b51'
                },
                    {
                        data: historic.lows,
                        backgroundColor: 'transparent',
                        borderColor: '#98d6ea',
                        borderWidth: 4,
                        pointBackgroundColor: '#98d6ea'
                    }]
            },
            options: {
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: false
                        },
                        scaleLabel: {
                            display: true,
                            labelString: 'Temperature',
                            fontStyle: 'bold'
                        }
                    }],
                    xAxes: [{
                        scaleLabel: {
                            display: true,
                            labelString: 'Month',
                            fontStyle: 'bold'
                        }
                    }]
                },
                legend: {
                    display: false
                }

            }
        });
    }
    handleUnitChange();
}

function displayCityImage(images) {
    if ($('#photo-section').length) {
        $('#photo-section').empty();
    }
    for (let url of images.urls) {
        $('<img>')
            .addClass('city-photo')
            .attr('src', url)
            .css('display', 'none')
            .appendTo($('#photo-section'));
    }
// 	slideshow();
 	$(".city-photo")[0].style.display = 'block';
}

var slideIndex = 0;    
function slideshow() {    
 	slides = $(".city-photo");
	if (slides.length > 0) {
        for (let slide of slides) {
            slide.style.display = 'none';
        }
        ++slideIndex;
        if (slideIndex > slides.length) {
            slideIndex = 1;
        }
        slides[slideIndex-1].style.display = 'block';
        setTimeout(slideshow, 5000);
    }
}

var months =["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

function getToday() {
    let today = new Date();
    let day = today.getDate();
    let month = today.getMonth();
    let year = today.getFullYear();
    return months[month] + " " + day + ", " + year;
}

function getForecastDates() {
    let today = new Date();
    let day = today.getDate();
    let month = today.getMonth();
    let dates = [];
    for (let i = 0; i < 5; ++i) {
        dates.push(months[month] + " " + (day + i));
    }
    return dates;
}

function handleUnitChange() {

}