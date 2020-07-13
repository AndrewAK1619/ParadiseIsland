angular.module('app')
.constant('REGION_NAMES_ENDPOINT', '/region/names')
.factory('Region', function($resource, REGION_NAMES_ENDPOINT) {
	return $resource(REGION_NAMES_ENDPOINT, {}, {

	});
})
.service('RegionService', function($resource, Region, REGION_NAMES_ENDPOINT) {
	this.getAllNames = () => $resource(REGION_NAMES_ENDPOINT).query();
});