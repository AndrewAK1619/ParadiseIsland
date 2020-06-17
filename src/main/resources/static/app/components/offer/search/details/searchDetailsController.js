angular.module('app')
.controller('SearchDetailsController', function ($routeParams, SearchDetailsService) {
	
	const vm = this;
	
	vm.hotelId = $routeParams.hotelId;
	
	vm.searchDetails = SearchDetailsService.getSearchDetails(vm.hotelId);
});