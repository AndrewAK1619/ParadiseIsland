angular.module('app')
.controller('SearchListController', function ($filter, $location, $cookies, $stateParams, 
		SearchService, HotelService, CountryService, RegionService, CityService) {
	
	const vm = this;
	vm.searchDataLoaded = false;
	vm.pageNumber = $stateParams.pageNumber;
	
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
	
    const differenceInDays = (dt1, dt2) => {
        var one = new Date(dt1[0], dt1[1], dt1[2]),
            two = new Date(dt2[0], dt2[1], dt2[2]);
        var millisecondsPerDay = 1000 * 60 * 60 * 24;
        var millisBetween = two.getTime() - one.getTime();
        var days = millisBetween / millisecondsPerDay;
    
        return Math.floor(days);      
    };
	
	const setSearchData = result => {
		vm.fileArray = result.fileList[0];
		vm.pageInfo = result.hotelList[0];
		vm.hotelArray = vm.pageInfo.content;
		vm.roomList = result.roomList[0];
		vm.airlineOffer = result.airlineOffer[0];
		
		const searchDataMap = JSON.parse($cookies.get('searchDataMap'));
		const departure = searchDataMap.departure;
		const returnDate = searchDataMap.returnDate;
		
        var dt1 = departure.split('-'),
	        dt2 = returnDate.split('-');

		vm.days = differenceInDays(dt1, dt2);
		
        if(dt1[0] === dt2[0]) {
        	vm.showDate = dt1[2] + '.' + dt1[1] + '-' + dt2[2] + '.' + 
				dt2[1] + '.' + dt2[0];
        } else {
        	vm.showDate = dt1[2] + '.' + dt1[1] + '.' + dt2[0] + '-' + 
    			dt2[2] + '.' + dt2[1] + '.' + dt2[0];
        }

		var num;
		for(num in vm.roomList) {
			if(!vm.roomList[num]) {
				vm.hotelArray[num].price = 'No room available';
				vm.hotelArray[num].isRoomNull = true;
			}
			else
				vm.hotelArray[num].price = 
					vm.roomList[num].roomPrice * vm.days + vm.airlineOffer.flightPrice;
		}
		
		checkVisibleButtons(vm.pageInfo);

		if(vm.pageInfo.totalElements === 0) {
			vm.resultFound = true;
		} else {
			vm.searchDataLoaded = true;
			vm.resultFound = false;
		}
		var oneImg;
		for(oneImg in vm.fileArray) {
			vm.hotelArray[oneImg].imgSrc = vm.fileArray[oneImg];
		}
	}
	
	const setSearchDataAndButtons = result => {
		vm.searchDataLoaded = false;
		vm.pageNumber = result.hotelList[0].number + 1;
		vm.totalPages = result.hotelList[0].totalPages;
		
		if(vm.pageNumber >= 1 && vm.pageNumber <= vm.totalPages) {
			setSearchData(result);
			setNumberButtons(vm.pageNumber);
		} else {
			$location.path(`/search-result/page/1`);
		}
	}

	if(vm.pageNumber) {
		if(vm.pageNumber < 1 || vm.pageNumber > vm.totalPages) {
			$location.path(`/search-result/page/1`);
		}
		vm.searchDataArray = SearchService.getAllSearchData(vm.pageNumber);
		vm.searchDataArray.$promise.then(setSearchDataAndButtons);
	} else {
		$location.path(`/search-result/page/1`);
	}

	vm.loadPage = buttonNumber => {
		if(buttonNumber >= 1 && buttonNumber <= vm.totalPages) {
			vm.pageNumber = buttonNumber;
			$location.path(`/search-result/page/${vm.pageNumber}`);
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

	vm.countriesNames = CountryService.getAllNames();
	vm.regionsNames = RegionService.getAllNames();
	vm.citiesNames = CityService.getAllNames();
	vm.hotels = SearchService.getAllHotels();

	const showDataList = (string, dataList) => {
		const output = [];
		angular.forEach(dataList, function(data) {
			if(data.toLowerCase().indexOf(string.toLowerCase()) >= 0) {
				output.push(data);
			}
		});
		return output;
	}
	vm.completeSearch = string => {
		vm.hideSearchList = false;
		if(string == undefined)
			string = '';
		
		const outputCountry = showDataList(string, vm.countriesNames);
		if(outputCountry.length == 0) {
			vm.isCountriesListExist = true;
		}
		else {
			vm.filterSearchCountry = outputCountry;
			vm.isCountriesListExist = false;
		}
		const outputRegion = showDataList(string, vm.regionsNames);
		if(outputRegion.length == 0){
			vm.isRegionsListExist = true;
		} else {
			vm.filterSearchRegion = outputRegion;
			vm.isRegionsListExist = false;
		}
		const outputCity = showDataList(string, vm.citiesNames);
		if(outputCity.length == 0){
			vm.isCitiesListExist = true;
		} else {
			vm.filterSearchCity = outputCity;
			vm.isCitiesListExist = false;
		}
		if(string !== ''){
			var outputHotel = [];
			angular.forEach(vm.hotels, function(hotel) {
				if(hotel.hotelName.toLowerCase().indexOf(string.toLowerCase()) >= 0) {
					outputHotel.push(hotel);
				}
			});
			if(outputHotel.length == 0){
				vm.isHotelsListExist = true;
			} else {
				vm.filterSearchHotel = outputHotel;
				vm.isHotelsListExist = false;
			}
		} else {
			vm.isHotelsListExist = true;
		}
	}
	vm.fillSearchTextbox = (chosenDestination, dataType) => {
		if(dataType === 'hotel'){
			vm.destination = chosenDestination.hotelName;
			vm.chosenDestination = chosenDestination.id;
		} else {
			vm.destination = chosenDestination;
			vm.chosenDestination = chosenDestination;
		}
		vm.dataType = dataType;
		vm.hideSearchList = true;
		vm.searchIsChosen = true;
	}
	
	const okCallback = () => {
		if($location.path() === '/search-result/page/1')
			location.reload();
		else
			$location.path(`/search-result/page/1`);
	};
	
	const errorCallback = err => {
		vm.msg=`Data write error: ${err.data.message}`;
	};
	
	vm.createCookieOfSearchDataAndRedirect = () => {
		
		if(!vm.departure)
			vm.departure = new Date();
		if(!vm.returnDate) {
			vm.returnDate = new Date();
			vm.returnDate.setDate(vm.departure.getDate() + 7);
		}
		
		departure = $filter('date')(vm.departure, "yyyy-MM-dd");
		returnDate = $filter('date')(vm.returnDate, "yyyy-MM-dd");
		
		const searchDataMap = { 
				"chosenDestination": vm.chosenDestination, 
				"dataType": vm.dataType,
				"departure": departure,
				"returnDate": returnDate,
				"persons": vm.persons};
		
		var expireDate = new Date();
		expireDate.setDate(expireDate.getDate() + 1);
		
		$cookies.put('searchDataMap', JSON.stringify(searchDataMap),
				{expires: expireDate, samesite: 'strict'});

		okCallback();
	}
	var init = setMinDate = () => {
		var now = new Date();
	    var day = ("0" + now.getDate()).slice(-2);
	    var month = ("0" + (now.getMonth() + 1)).slice(-2);
	    var today = now.getFullYear() + "-" + (month) + "-" + (day);
		$('#dateDeparture').attr('min', today);
		$('#dateReturn').attr('min', today);
	};
	vm.setMinReturnDate = departure => {
		if(departure === null || departure === 'undefined') {
			init();
		} else {
			var day = ("0" + departure.getDate()).slice(-2);
			var month = ("0" + (departure.getMonth() + 1)).slice(-2);
			var year = departure.getFullYear();
			var startDate = (year) + "-" + (month) + "-" + (day);
			$('#dateReturn').attr('min', startDate);
		}
	}
	init();
});