angular.module('app')
.controller('HomeController', function(HomeService) {
	const vm = this;

	vm.countriesNames = HomeService.getAllCountries();
	vm.regionsNames = HomeService.getAllRegions();
	vm.citiesNames = HomeService.getAllCities();
	vm.hotels = HomeService.getAllHotels();
	
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
	vm.searchDataSend = () => {
		vm.searchData = {'chosenDestination':vm.chosenDestination, 
				'dataType':vm.dataType,
				'departure':vm.departure,
				'returnDate':vm.returnDate,
				'persons':vm.persons};
		HomeService.sendDataSearch();
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