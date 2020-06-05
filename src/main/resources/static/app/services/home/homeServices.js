angular.module('app')
.constant('HOME_ENDPOINT', '/')
.constant('HOTELS_ENDPOINT', '/all/hotels')
.constant('COUNTRIES_ENDPOINT', '/all/countries')
.constant('REGIONS_ENDPOINT', '/all/regions')
.constant('CITIES_ENDPOINT', '/all/cities')
.constant('SEARCH_ENDPOINT', '/search')
.factory('Home', function ($resource, HOME_ENDPOINT, SEARCH_ENDPOINT) {
	return $resource(HOME_ENDPOINT, {}, {
		sendDataSearch: {
			method: 'POST',
			url: SEARCH_ENDPOINT,
			transformRequest: angular.identity,
			headers: {'Content-Type': undefined}
		},
	});
})
.service('HomeService', function ($resource, Home, HOTELS_ENDPOINT, 
		COUNTRIES_ENDPOINT, REGIONS_ENDPOINT, CITIES_ENDPOINT) {
	this.getAllHotels = () => $resource(HOTELS_ENDPOINT).query();
	this.getAllCountries = () => $resource(COUNTRIES_ENDPOINT).query();
	this.getAllRegions = () => $resource(REGIONS_ENDPOINT).query();
	this.getAllCities = () => $resource(CITIES_ENDPOINT).query();
	this.sendDataSearch = searchData => Home.sendDataSearch(searchData);
});