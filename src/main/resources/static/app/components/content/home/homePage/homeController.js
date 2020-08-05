angular.module('app')
.controller('HomeController', function($filter, $location, $cookies, 
		SearchService, CountryService, RegionService, CityService) {
	
	const vm = this;

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