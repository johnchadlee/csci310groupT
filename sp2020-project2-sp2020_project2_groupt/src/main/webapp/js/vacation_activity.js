let locationsC;
let currentLocations = locationsC;
let rowArray; // holds all of the rows so they can be reordered
let tempUnits = 'F';
let distanceIdx;
let ascending = false; // gets flipped the first time

function initializeTemp(toggleSelector, resultsTable, tableHeaders, tableDatas){
	$.get({
		url: "/api/temperature",
		dataType: 'JSON',
		success: (result) => { initializeToggle(toggleSelector, result.tempUnits, resultsTable, tableHeaders, tableDatas); }
	});
}

//initialize toggle
function initializeToggle(toggleSelector, units, resultsTable, tableHeaders, tableDatas){
	$(function(){
		toggleSelector.bootstrapToggle({
			on: '°F',
			off: '°C'
		});
	});
	tempUnits = units;
	if(tempUnits === 'C'){
		toggleSelector.bootstrapToggle('off');
	}
	else{
		toggleSelector.bootstrapToggle('on');
	}
	toggleSelector.change(function(){
		ascending = !ascending;
		let locations;
		if(tempUnits === 'C'){
			tempUnits = 'F';
			locations = locationsF;
		}
		else{
			tempUnits = 'C';
			locations = locationsC;
		}
		let data = {
				tempUnits: tempUnits
			};
		data = JSON.stringify(data);
		$.post({
			url: "/api/temperature",
			data: data
		})
		if(rowArray != null){
			displayResults(locations, resultsTable, tableHeaders, tableDatas);
		}
	})
}

function hideErrors(resultsTable, inputs){
	resultsTable.empty();
	for(let input of inputs){
		$('#'+input).removeClass('error-input');
	}
}
function showErrors(errors, resultsTable){
	for(let error of errors){
		resultsTable.append($("<div class=\"error-text\">illegal value for asdfinput " + error + "</div>"));
		$('#' + error).addClass('error-input');
	}
}

function createTableHeader(resultsTable, tableHeaders){
	const thead = $("<thead></thead>");
	for(let header of tableHeaders){
		thead.append("<th class='header' for=\"col\">"+  header +"</th>");
	}
	const dist = $('<th class="header" id="distance">Distance &#9650</th>');
	initDistanceSort(dist, tableDatas, resultsTable);
	thead.append(dist);
	thead.append("<th></th>");
	resultsTable.append(thead);
}

function makeAddToFavoritesBtn(button, city, country){
	button.text("Add to favorites");
	button.on('click', function(){
		makeRemoveFromFavoritesBtn(button, city, country);
		$.post({
			url: "/api/favorites/add",
			data: {
				city: city,
				country: country
			}
		});
	});
}

function makeRemoveFromFavoritesBtn(button, city, country){
	button.text("Remove from favorites");
	button.on('click', function(){
		makeAddToFavoritesBtn(button, city, country);
		$.post({
			url: "/api/favorites/remove",
			data: {
				city: city,
				country: country
			}
		});
	});
}

function makeFavButton(location){
	const button = $('<button class="btn btn-outline-secondary fav-btn"></button>');
	if(location.favorite){
		makeAddToFavoritesBtn(button, location.city, location.country);
	}
	else{
		makeRemoveFromFavoritesBtn(button, location.city, location.country);
	}
	return button;
}

// order = 'asc'|'desc'
function makeRowArray(locations, tableDatas, order){ 
	let row;
	rowArray = [];
	for(let location of locations){
		row = $("<tr class=\"result-row\"></tr>");
		let td;
		for(let attr of tableDatas){
			td = $("<td>" + location[attr] + "</td>");
			row.append(td);
		}
		row.append(makeFavButton(location));
		row.children()[distanceIdx].classList.add('distance');
		rowArray.push(row);
		// resultsTable.append(row);
	}
	return rowArray;
}

function displayRows(resultsTable, rowArray){
	$('tbody').empty(); // this is a hack

	// clear dark rows
	rowArray.forEach((row) => {
		row.removeClass('dark-row');
	});
	let dark = false;
	for(let row of rowArray){
		if(dark){
			row.addClass('dark-row');
		}
		dark = !dark;
		resultsTable.append(row);
	}
}

function displayResults(locations, resultsTable, tableHeaders, tableDatas){
	resultsTable.empty();
	if(locations.length == 0){
		resultsTable.append("No locations found");
	}
	else{
		createTableHeader(resultsTable, tableHeaders);
		makeRowArray(locations, tableDatas);
		// ascending = true;
		sortRows(rowArray);
		displayRows(resultsTable, rowArray);
	}
}

// $('#search-form').on('submit', function(){
// 	event.preventDefault();
function initializeSearch(formSelector, url, resultsTable, inputs, tableHeaders, tableDatas){
	formSelector.on('submit', function(){
		event.preventDefault();
		hideErrors(resultsTable, inputs);
		rowArray = null;
		$.get({
			// url: '/api/vacationPlanning/search',
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
					displayResults(locations, resultsTable, tableHeaders, tableDatas);
				}
				else{
					showErrors(response.errors, resultsTable);
				}
			}
		});
	});
	// return false;
}

function setDistanceIdx(tableDatas){
	distanceIdx = tableDatas.indexOf('distance');
}

function asc(a, b){
	return parseInt(a[0].children[distanceIdx].innerHTML) - parseInt(b[0].children[distanceIdx].innerHTML);
}
function desc(a, b){
	return parseInt(b[0].children[distanceIdx].innerHTML) - parseInt(a[0].children[distanceIdx].innerHTML);
}

function sortRows(rowArray){
	ascending = !ascending;
	rowArray.reverse();
	let cmp = desc;
	if(ascending){
		cmp = asc;
	}
	rowArray.sort(cmp);
}

function initDistanceSort(distanceHeader, tableDatas, resultsTable){
	distanceHeader.on('click', function(){
		var text = $('distanceHeader').html();
		text = text.substring(0, text.length - 1)
		if(ascending){
			text += '&#9650';
		}
		else{
			text += '&#9660';
		}
		sortRows(rowArray);
		displayRows(resultsTable, rowArray);
	});
}