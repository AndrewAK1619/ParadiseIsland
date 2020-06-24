angular.module('app')
.controller('AirlineOfferListController', function($routeParams, $location, AirlineOfferService, SearchDetailsService) {
	const vm = this;
	
	vm.hotelId = $routeParams.hotelId;
	
	const setAirlinesOfferListData = result => {
		vm.airlinesOfferList = result;
		
		for(var i = 0; i < vm.airlinesOfferList.length; i++) {
			vm.airlinesOfferList[i].numId = i+1;
		}
	}

	vm.airlinesOfferListPromise = AirlineOfferService.getAirlinesOfferByDate(vm.hotelId);
	vm.airlinesOfferListPromise.$promise.then(setAirlinesOfferListData);
	
	vm.search = airlineName => {
		vm.airlinesOfferList = AirlineOfferService.getAirlinesOfferByDate(vm.hotelId, airlineName);
	};
	
	vm.setAirlineOffer = airlineOffer => {
		SearchDetailsService.setAirlineOfferAndHotelId(airlineOffer, vm.hotelId);
		$location.path(`/search-result/details/${vm.hotelId}`);
	};
});