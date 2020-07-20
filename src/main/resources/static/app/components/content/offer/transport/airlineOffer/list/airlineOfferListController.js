angular.module('app')
.controller('AirlineOfferListController', function($stateParams, $location, AirlineOfferService, SearchDetailsService) {
	const vm = this;
	
	vm.hotelId = $stateParams.hotelId;
	
	const setNumId = () => {
		for(var i = 0; i < vm.airlinesOfferList.length; i++) {
			vm.airlinesOfferList[i].numId = i+1;
		}
	}
	
	const setAirlinesOfferListData = result => {
		vm.airlinesOfferList = result;
		setNumId();
	}

	vm.airlinesOfferListPromise = AirlineOfferService.getAirlinesOfferByDate(vm.hotelId);
	vm.airlinesOfferListPromise.$promise.then(setAirlinesOfferListData);
	
	vm.search = airlineName => {
		vm.airlinesOfferList = AirlineOfferService.getAirlinesOfferByDate(vm.hotelId, airlineName);
		vm.airlinesOfferList.$promise.then(setNumId);
	};
	
	vm.setAirlineOffer = airlineOffer => {
		SearchDetailsService.setAirlineOfferAndHotelId(airlineOffer, vm.hotelId);
		$location.path(`/search-result/details/${vm.hotelId}`);
	};
});