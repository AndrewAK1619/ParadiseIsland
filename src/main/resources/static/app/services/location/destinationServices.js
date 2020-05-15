angular.module('app')
.constant('DESTINATION_POPULAR_ENDPOINT', '/destinations/popular')
.constant('DESTINATION_RANDOM_ENDPOINT', '/destinations/random')
.factory('Destination', function($resource, 
		DESTINATION_POPULAR_ENDPOINT, 
		DESTINATION_RANDOM_ENDPOINT) {
	return $resource(DESTINATION_POPULAR_ENDPOINT, {}, {
		getPopular: {
			method: 'GET',
			transformRequest: angular.identity,
			headers: {'Content-type': undefined}
		},
		getRandom: {
			method: 'GET',
			transformRequest: angular.identity,
			headers: {'Content-type': undefined},
			url: DESTINATION_RANDOM_ENDPOINT
		}
	});
})
.service('DestinationService', function (Destination) {
	this.getPopular = () => Destination.getPopular();
	this.getRandom = () => Destination.getRandom();
});