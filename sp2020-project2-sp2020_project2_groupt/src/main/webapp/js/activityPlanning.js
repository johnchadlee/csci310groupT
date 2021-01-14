const resultsTable = $('#results');
const inputs = ["activity", "numResults", "location"];
const url = '/api/activityPlanning/search';
const tableHeaders = ["City", "Country", "Current temp"];
const tableDatas = ["city", "country",  "currentTemp", "distance"];
const toggleSelector = $('#tempToggle');
const formSelector = $('#search-form');

const activityInput = $('#activity');
const activityUrl = '/api/activityPlanning/activities'

const distanceHeader = $('#distance');


initializeTemp(toggleSelector, resultsTable, tableHeaders, tableDatas);
setDistanceIdx(tableDatas);
initializeSearch(formSelector, url, resultsTable, inputs, tableHeaders, tableDatas);

// initDistanceSort(distanceHeader, tableDatas, resultsTable);

function initializeAutocomplete(response){
	console.log(response);
	$( function() {
		activityInput.autocomplete({
			source: response.activities
		});
	});
}

$.get({
	url: activityUrl,
	dataType: 'JSON',
	success: initializeAutocomplete
});

// const hiddenDiv = $('#hidden-autocomplete-div');
// $( "#activity" ).on( "autocompleteresponse", function( event, ui ) {
// 	// console.log(ui);
// 	hiddenDiv.empty();
// 	for(let option of ui.content){
// 		hiddenDiv.append("<span class='autocomplete-option'>" + option.value + "</span>");
// 	}
// });
