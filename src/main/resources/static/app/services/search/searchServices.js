angular.module('app')
.constant('SEARCH_ENDPOINT', '/search-result/page/:pageNumber')
.constant('HOTELS_ENDPOINT', '/search-result/all/hotels')
.factory('Search', function ($resource, SEARCH_ENDPOINT) {
	return $resource(SEARCH_ENDPOINT, {}, {
		getAllSearchData: {
			method: 'GET',
			params: { pageNumber: '@_pageNumber' },
			transformRequest: angular.identity,
			headers: {'Content-type': undefined}
		},
	});
})
.service('SearchService', function ($resource, Search, HOTELS_ENDPOINT) {
	this.getAllSearchData = (pageNumber) => 
		Search.getAllSearchData({pageNumber: pageNumber});
	this.getAllHotels = () => $resource(HOTELS_ENDPOINT).query();
});