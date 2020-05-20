angular.module('app')
.constant('COUNTRY_NAMES_ENDPOINT', '/country/names')
.factory('Country', function($resource, COUNTRY_NAMES_ENDPOINT) {
	return $resource(COUNTRY_NAMES_ENDPOINT, {}, {

	});
})
.service('CountryService', function($resource, Country, COUNTRY_NAMES_ENDPOINT) {
	this.getAllNames = () => $resource(COUNTRY_NAMES_ENDPOINT).query();
});