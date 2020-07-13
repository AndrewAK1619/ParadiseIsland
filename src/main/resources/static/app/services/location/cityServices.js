angular.module('app')
.constant('CITY_NAMES_ENDPOINT', '/cities/names')
.constant('CITY_NAMES_COUNTRY_ENDPOINT', '/cities/names/country')
.factory('City', function($resource, CITY_NAMES_ENDPOINT, CITY_NAMES_COUNTRY_ENDPOINT) {
	return $resource(CITY_NAMES_ENDPOINT, {}, {
		getAllNamesByCountry: {
			method: 'GET',
			url: CITY_NAMES_COUNTRY_ENDPOINT,
			transformRequest: angular.identity,
			headers: {'Content-type': undefined},
			isArray: true
		}
	});
})
.service('CityService', function($resource, City, CITY_NAMES_ENDPOINT) {
	this.getAllNames = () => $resource(CITY_NAMES_ENDPOINT).query();
	this.getAllNamesByCountry = (country) => City.getAllNamesByCountry({country});
});