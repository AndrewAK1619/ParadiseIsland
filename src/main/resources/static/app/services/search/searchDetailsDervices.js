angular.module('app')
.constant('SEARCH_DETAILS_ENDPOINT', '/search-result/details/:hotelId')
.factory('SearchDetails', function ($resource, SEARCH_DETAILS_ENDPOINT) {
	return $resource(SEARCH_DETAILS_ENDPOINT, {}, {
		getSearchDetails: {
			method: 'GET',
			params: { hotelId: '@_hotelId' },
			transformRequest: angular.identity,
			headers: {'Content-type': undefined}
		},
	});
})
.service('SearchDetailsService', function (SearchDetails) {
	this.getSearchDetails = hotelId => SearchDetails.getSearchDetails({hotelId: hotelId});
});