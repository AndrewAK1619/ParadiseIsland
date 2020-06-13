angular.module('app')
.constant('HOME_ENDPOINT', '/')
.factory('Home', function ($resource, HOME_ENDPOINT) {
	return $resource(HOME_ENDPOINT, {}, {

	});
})
.service('HomeService', function (Home) {

});