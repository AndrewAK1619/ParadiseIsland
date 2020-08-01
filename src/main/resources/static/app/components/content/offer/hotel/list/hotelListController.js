angular.module('app')
.controller('HotelListController', function ($rootScope, $stateParams, $location, 
		$rootScope, $state, HotelService, CountryService) {
	
	const vm = this;
	
	vm.hotelDataLoaded = false;
	vm.pageNumber = $stateParams.pageNumber;
	vm.countriesNames = CountryService.getAllNames();
	vm.hotelName = $rootScope.hotelName;
	vm.countryName = $rootScope.countryName;

	vm.completeCountry = string => {
		vm.hideCountryList = false;
		var output = [];
		angular.forEach(vm.countriesNames, function(country) {
			if(string == undefined) {
				string = '';
			} else if(country.toLowerCase().indexOf(string.toLowerCase()) >= 0) {
				output.push(country);
			}
		});
		vm.filterCountry = output;
	}
	vm.fillCountryTextbox = function(string) {
		vm.countryName = string;
		vm.hideCountryList = true;
		vm.countryIsChosen = true;
	}
	
	const removeClassButtons = () => {
		angular.element(document.querySelector("#firstButton")).removeClass("active");
		angular.element(document.querySelector("#secondButton")).removeClass("active");
		angular.element(document.querySelector("#thirdButton")).removeClass("active");
		angular.element(document.querySelector("#fourthButton")).removeClass("active");
		angular.element(document.querySelector("#fifthButton")).removeClass("active");
	}
	
	const setNumberButtons = pageNum => {
		removeClassButtons();
		if(pageNum === 1 || pageNum === 2 || pageNum === 3) {
			vm.numButton = 3;
			if(pageNum === 1) {
				angular.element(document.querySelector("#firstButton")).addClass("active");
			} else if(pageNum === 2) {
				angular.element(document.querySelector("#secondButton")).addClass("active");
			} else if(pageNum === 3) {
				angular.element(document.querySelector("#thirdButton")).addClass("active");
			}
		} else if(pageNum === vm.totalPages - 2 || pageNum === vm.totalPages - 1 || 
				pageNum === vm.totalPages) {
			
			vm.numButton = vm.totalPages - 2;
			if(pageNum === vm.totalPages - 2) {
				angular.element(document.querySelector("#thirdButton")).addClass("active");
			} else if(pageNum === vm.totalPages - 1) {
				angular.element(document.querySelector("#fourthButton")).addClass("active");
			} else if(pageNum === vm.totalPages) {
				angular.element(document.querySelector("#fifthButton")).addClass("active");
			}
		} else {
			vm.numButton = vm.pageNumber;
			angular.element(document.querySelector("#thirdButton")).addClass("active");
		}
	}
	
	const checkVisibleButtons = pageInfo => {
		vm.disableFirstPage = pageInfo.first;
		vm.disablePreviousPage = pageInfo.first;
		vm.disableNextPage = pageInfo.last;
		vm.disableLastPage = pageInfo.last;
		
		if(pageInfo.totalPages === 1) {
			vm.showSecondButton = true;
			vm.showThirdButton = true;
			vm.showFourthButton = true;
			vm.showFifthButton = true;
		} else if(pageInfo.totalPages === 2) {
			vm.showSecondButton = false;
			vm.showThirdButton = true;
			vm.showFourthButton = true;
			vm.showFifthButton = true;
		} else if(pageInfo.totalPages === 3) {
			vm.showSecondButton = false;
			vm.showThirdButton = false;
			vm.showFourthButton = true;
			vm.showFifthButton = true;
		} else if(pageInfo.totalPages === 4) {
			vm.showSecondButton = false;
			vm.showThirdButton = false;
			vm.showFourthButton = false;
			vm.showFifthButton = true;
		} else {
			vm.showSecondButton = false;
			vm.showThirdButton = false;
			vm.showFourthButton = false;
			vm.showFifthButton = false;
		}
	}
	
	const setHotlAndTopImgData = result => {
		vm.fileArray = result.fileList[0];
		vm.pageInfo = result.hotelList[0];
		vm.hotelArray = vm.pageInfo.content;
		checkVisibleButtons(vm.pageInfo);
		
		if(vm.pageInfo.totalElements === 0) {
			vm.resultFound = true;
		} else {
			vm.hotelDataLoaded = true;
			vm.resultFound = false;
		}
		var oneImg;
		for(oneImg in vm.fileArray) {
			vm.hotelArray[oneImg].imgSrc = vm.fileArray[oneImg];
		}
	}
	
	const setHotelAndTopImg = result => {
		vm.hotelDataLoaded = false;
		vm.pageNumber = result.hotelList[0].number + 1;
		vm.totalPages = result.hotelList[0].totalPages;
		
		if($location.path() === '/admin/hotels') {
			setHotlAndTopImgData(result);
			setNumberButtons(vm.pageNumber);
		} else if(vm.pageNumber >= 1 && vm.pageNumber <= vm.totalPages) {
			setHotlAndTopImgData(result);
			setNumberButtons(vm.pageNumber);
		} else {
			$location.path(`/admin/hotels/page/1`);
		}
	}

	vm.confirmMsg = 'Are you sure to delete this hotel ? \n \n' +
		'This can lead to serious consequences, \n' +
		'for instance, delete existing bookings.';

	const errorCallback = err => {
		vm.msg=`${err.data.message}`;
	};

	const deleteCallback = () => {
		$state.reload();
	}

	vm.deleteHotel = (hotel) => {
		HotelService.deleteHotel(hotel)
			.$promise
			.then(deleteCallback)
			.catch(errorCallback);
	};

	if(vm.pageNumber) {
		if(vm.pageNumber < 1 || vm.pageNumber > vm.totalPages) {
			$location.path(`/admin/hotels`);
		}
		vm.hotelsAndTopImgArray = HotelService
			.getAllHotelsByNameAndCountry(vm.pageNumber, vm.hotelName, vm.countryName);
		vm.hotelsAndTopImgArray.$promise.then(setHotelAndTopImg);
	} else {
		vm.hotelsAndTopImgArray = HotelService.getAllHotelsAndMainImg();
		vm.hotelsAndTopImgArray.$promise.then(setHotelAndTopImg);
	}
	
	vm.search = () => {
		$rootScope.hotelName = vm.hotelName;
		$rootScope.countryName = vm.countryName;
		if(vm.pageNumber === 1) {
			vm.hotelsAndTopImgArray = HotelService
				.getAllHotelsByNameAndCountry(vm.pageNumber, vm.hotelName, vm.countryName);
			vm.hotelsAndTopImgArray.$promise.then(setHotelAndTopImg);
		} else {
			vm.pageNumber = 1;
			$location.path(`/admin/hotels/page/${vm.pageNumber}`);
		}
	};

	vm.loadPage = buttonNumber => {
		if(buttonNumber >= 1 && buttonNumber <= vm.totalPages) {
			vm.pageNumber = buttonNumber;
			$rootScope.hotelName = vm.hotelName;
			$rootScope.countryName = vm.countryName;
			$location.path(`/admin/hotels/page/${vm.pageNumber}`);
		}
	};
	vm.firstPage = () => {
		vm.loadPage(1);
	}
	vm.previousPage = () => {
		if(vm.pageNumber > 1) {
			vm.pageNumber--;
			vm.loadPage(vm.pageNumber);
		}
	};
	vm.nextPage = () => {
		if(vm.pageNumber < vm.totalPages) {
			vm.pageNumber++;
			vm.loadPage(vm.pageNumber);
		}
	};
	vm.lastPage = () => {
		vm.loadPage(vm.totalPages);
	};
});