angular.module('app')
.controller('DestinationDetailsController', function ($stateParams, $location, 
		DestinationService) {
	const vm = this;
	vm.countryId = $stateParams.id;

	const splitCountryInfo = countryInfo => {
		const lengh = countryInfo.length;
		vm.firstCountryInfo = countryInfo.slice(0, lengh/2);
		vm.secondCountryInfo = countryInfo.slice(lengh/2, lengh);
	}
	const setCountryAndTopImg = result => {
		vm.country = result.country[0];
		splitCountryInfo(vm.country.countryInformationDto);
		vm.img = result.file[0];
	}
	vm.destinationDetailsAndTopImg = DestinationService.getDetails(vm.countryId);
	vm.destinationDetailsAndTopImg.$promise.then(setCountryAndTopImg);
	
	vm.returnPage = () => {
		const destUrl = DestinationService.getDestinationListUrl();
		if(!destUrl)
			$location.path('/destinations/popular');
		else
			$location.path(destUrl);
	}
});