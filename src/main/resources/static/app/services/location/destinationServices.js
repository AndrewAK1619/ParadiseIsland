angular.module('app')
.constant('DESTINATION_POPULAR_ENDPOINT', '/destinations/popular')
.constant('DESTINATION_RANDOM_ENDPOINT', '/destinations/random')
.constant('DESTINATION_DETAILS_ENDPOINT', '/destinations/details/:id')
.factory('Destination', function($resource, DESTINATION_POPULAR_ENDPOINT, 
		DESTINATION_RANDOM_ENDPOINT, DESTINATION_DETAILS_ENDPOINT) {
	return $resource(DESTINATION_POPULAR_ENDPOINT, { id: '@_id' }, {
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
		},
		getDetails: {
			method: 'GET',
			transformRequest: angular.identity,
			headers: {'Content-type': undefined},
			url: DESTINATION_DETAILS_ENDPOINT
		}
	});
})
.factory('DestinationListUrl', function() {
	var destinationListUrl;
	
	var setDestinationListUrl = dest => {
		destinationListUrl = dest;
	};
	var getDestinationListUrl = function() {
		return destinationListUrl;
	};
	return {
		setDestinationListUrl: setDestinationListUrl,
		getDestinationListUrl: getDestinationListUrl
	};
})
.service('DestinationService', function (Destination, DestinationListUrl) {
	this.getPopular = () => Destination.getPopular();
	this.getRandom = () => Destination.getRandom();
	this.getDetails = id => Destination.getDetails({id: id});
	this.setDestinationListUrl = dest => DestinationListUrl.setDestinationListUrl(dest);
	this.getDestinationListUrl = () => DestinationListUrl.getDestinationListUrl();
});