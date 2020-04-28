angular.module('app')
.controller('DestinationListController', function (DestinationService) {
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
	
	vm.destinationAndTopImgArray = DestinationService.getTen();
	vm.destinationAndTopImgArray.$promise.then(setCountryAndTopImg);
});