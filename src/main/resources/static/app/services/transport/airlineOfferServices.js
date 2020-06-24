angular.module('app')
.constant('AIRLINE_OFFER_ENDPOINT', '/search-result/details/:hotelId/airlines')
.factory('AirlineOffer', function($resource, AIRLINE_OFFER_ENDPOINT) {
	return $resource(AIRLINE_OFFER_ENDPOINT, { hotelId: '@_hotelId' }, {
		getAirlinesOfferByDate: {
			method: 'GET',
			params: { hotelId: '@_hotelId' },
			transformRequest: angular.identity,
			headers: {'Content-type': undefined},
			isArray: true
		},
	});
})
.service('AirlineOfferService', function(AirlineOffer) {
	this.getAirlinesOfferByDate = (hotelId, airlineName) => AirlineOffer.getAirlinesOfferByDate({hotelId: hotelId, airlineName});
});