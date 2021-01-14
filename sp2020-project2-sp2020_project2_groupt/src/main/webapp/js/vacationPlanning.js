const resultsTable = $('#results');
const inputs = ["tempRangeLow", "tempRangeHigh", "numResults", "location"];
const url = '/api/vacationPlanning/search';
const tableHeaders = ["City", "Country", "Avg. Min. Temp.", "Avg. Max. Temp."];
const tableDatas = ["city", "country", "avgMinTemp", "avgMaxTemp", "distance"];
const toggleSelector = $('#tempToggle');
const formSelector = $('#search-form');

const distanceHeader = $('#distance');

initializeTemp(toggleSelector, resultsTable, tableHeaders, tableDatas);
setDistanceIdx(tableDatas);
initializeSearch(formSelector, url, resultsTable, inputs, tableHeaders, tableDatas);