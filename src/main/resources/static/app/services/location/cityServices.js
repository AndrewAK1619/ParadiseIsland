angular.module('app')
.constant('CITY_NAMES_ENDPOINT', '/cities/names')
.factory('City', function($resource, CITY_NAMES_ENDPOINT) {
	return $resource(CITY_NAMES_ENDPOINT, {}, {
		getAllNames: {
			method: 'GET',
			transformRequest: angular.identity,
			headers: {'Content-type': undefined},
			isArray: true
		}
	});
})
.service('CityService', function(City) {
	this.getAllNames = (country) => City.getAllNames({country});
});