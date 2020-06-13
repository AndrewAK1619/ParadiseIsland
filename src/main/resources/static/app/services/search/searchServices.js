angular.module('app')
.constant('HOTELS_ENDPOINT', '/all/hotels')
.constant('COUNTRIES_ENDPOINT', '/all/countries')
.constant('REGIONS_ENDPOINT', '/all/regions')
.constant('CITIES_ENDPOINT', '/all/cities')
.constant('SEARCH_ENDPOINT', '/search-result/page/:pageNumber')
.factory('Search', function ($resource, SEARCH_ENDPOINT) {
	return $resource(SEARCH_ENDPOINT, {}, {
		getAllSearchData: {
			method: 'GET',
			params: { pageNumber: '@_pageNumber' },
			transformRequest: angular.identity,
			headers: {'Content-type': undefined}
		},
	});
})
.service('SearchService', function ($resource, Search, HOTELS_ENDPOINT, 
		COUNTRIES_ENDPOINT, REGIONS_ENDPOINT, CITIES_ENDPOINT) {
	this.getAllSearchData = (pageNumber) => 
		Search.getAllSearchData({pageNumber: pageNumber});
	this.getAllHotels = () => $resource(HOTELS_ENDPOINT).query();
	this.getAllCountries = () => $resource(COUNTRIES_ENDPOINT).query();
	this.getAllRegions = () => $resource(REGIONS_ENDPOINT).query();
	this.getAllCities = () => $resource(CITIES_ENDPOINT).query();
});