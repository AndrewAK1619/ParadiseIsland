angular.module('app')
.controller('DestinationListController', function ($location, DestinationService) {
	const vm = this;
	
	const setCountryAndTopImg = result => {
		vm.countryArray = result.countryList;
		vm.fileArray = result.fileList;
		vm.destination = vm.countryArray[0];
		vm.file = vm.fileArray[0];
		
		var oneImg;
		for(oneImg in vm.file) {
			vm.destination[oneImg].imgSrc = vm.file[oneImg];
		}
	}
	
	var searchObject = $location.url();
	if(searchObject === '/destinations/popular') {
		DestinationService.setDestinationListUrl(searchObject);
		vm.destinationAndTopImgArray = DestinationService.getPopular();
	} else if(searchObject === '/destinations/random') {
		DestinationService.setDestinationListUrl(searchObject);
		vm.destinationAndTopImgArray = DestinationService.getRandom();
	}
	
	vm.destinationAndTopImgArray.$promise.then(setCountryAndTopImg);
});