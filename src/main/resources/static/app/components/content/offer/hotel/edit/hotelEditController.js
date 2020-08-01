angular.module('app')
.controller('HotelEditController', function($stateParams, $location, $timeout, 
		HotelService, Hotel, CountryService, CityService) {
	const vm = this;
	const hotelId = $stateParams.hotelId;
	
	const setHotelAndTopImg = result => {
		vm.hotelArray = result.hotel;
		vm.fileArray = result.file;
		vm.hotel = vm.hotelArray[0];
		vm.hotel.city = vm.hotel.city + ' (' + vm.hotel.region + ')';
		vm.imgSrc = vm.fileArray[0];
	}
    
	const setDefaultImg = result => {
		vm.file = result.file;
		vm.imgSrc = vm.file[0];
	}
	
	if(hotelId) {
		vm.hotelAndTopImgArray = HotelService.get(hotelId);
		vm.hotelAndTopImgArray.$promise.then(setHotelAndTopImg);
	} else {
		vm.hotel = new Hotel();
		vm.defaultImg = HotelService.getDefaultImage();
		vm.defaultImg.$promise.then(setDefaultImg);
	}

	vm.countriesNames = CountryService.getAllNames();
	
	vm.completeCountry = function(string) {
		vm.hideCountry = false;
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
		vm.errorCountryMessage = false;
		vm.hotel.country = string;
		vm.hideCountry = true;
		vm.hotel.city = null;
		vm.citiesNames = CityService.getAllNamesByCountry(vm.hotel.country);
		vm.countryIsChosen = true;
	}
	
	vm.completeCity = function(string) {
		vm.hideCity = false;
		var output = [];
		angular.forEach(vm.citiesNames, function(city) {
			if(string == undefined) {
				string = '';
			} else if(city.toLowerCase().substring(0, city.lastIndexOf(' '))
					.indexOf(string.toLowerCase()) >= 0) {
				output.push(city);
			}
		});
		vm.filterCity = output;
	}
	vm.fillCityTextbox = function(string) {
		vm.errorCityMessage = false;
		vm.hotel.city = string;
		vm.hideCity = true;
		vm.cityIsChosen = true;
	} 

	const valid = response => {
		vm.hasError = false;
		vm.fields = response.fields;
		vm.messages = response.messages;
		
		if(vm.fields) {
			vm.hasError = true;
			for(var i = 0; i < vm.fields.length; i++) {
				if(vm.fields[i] === 'hotelName') {
					vm.errHotelName = vm.fields[i];
					vm.errHotelNameMsg = vm.messages[i];
				} else if (vm.fields[i] === 'description') {
					vm.errDescription = vm.fields[i];
					vm.errDescriptionMsg = vm.messages[i];
				} else if (vm.fields[i] === 'country') {
					vm.errCountryName = vm.fields[i];
					vm.errCountryNameMsg = vm.messages[i];
				} else if (vm.fields[i] === 'region') {
					vm.errRegionName = vm.fields[i];
					vm.errRegionNameMsg = vm.messages[i];
				} else if (vm.fields[i] === 'city') {
					vm.errCityName = vm.fields[i];
					vm.errCityNameMsg = vm.messages[i];
				}
			}
		}
	}
	
	const setNull = () => {
		delete vm.hotel.fields;
		delete vm.hotel.messages;
		vm.errHotelName = null;
		vm.errHotelNameMsg = null;
		vm.errDescription = null;
		vm.errDescriptionMsg = null;
		vm.errCountryName =null;
		vm.errCountryNameMsg = null;
		vm.errRegionName = null;
		vm.errRegionNameMsg = null;
		vm.errCityName = null;
		vm.errCityNameMsg = null;
	}
	
	const saveCallback = response => {
		valid(response);
		if(!vm.hasError) {
			vm.hasError = null;
			vm.hotel.id = response.id;
			$location.path(`/admin/hotels-edit/${vm.hotel.id}`);
		}
	};
	const errorCallback = err => {
		vm.msg=`${err.data.message}`;
	};
	
	vm.file;
	vm.saveHotel = () => {
		setNull();
		const maxFileSize = '3145728';
		if(vm.file.size > maxFileSize) {
			vm.msg='Data write error: maximum file size is 3MB';
		} else {
			const formData = new FormData();
			const cityRegion = vm.hotel.city;

			vm.errorCountryMessage = false;
			vm.errorCityMessage = false;

			if (vm.countryIsChosen && vm.cityIsChosen) {
				vm.hotel.region = cityRegion.substring(
						cityRegion.lastIndexOf('(') + 1, cityRegion.length - 1);
				vm.hotel.city = cityRegion.substr(0, cityRegion.indexOf(' '));
			} else if (!vm.countryIsChosen && !vm.cityIsChosen) {
				vm.errorCountryMessage = true;
				vm.errorCityMessage = true;
				throw new Error('Country and City must be selected from the list.');
			} else if (!vm.countryIsChosen) {
				vm.errorCountryMessage = true;
				throw new Error('Country must be selected from the list.');
			} else if (!vm.cityIsChosen) {
				vm.errorCityMessage = true;
				throw new Error('City must be selected from the list.');
			}
			formData.append('file', vm.file);
			formData.append('hotelDto', JSON.stringify(vm.hotel));
			
			HotelService.uploadFileAndHotel(formData)
				.$promise
				.then(saveCallback)
				.catch(errorCallback);
		}
	};
	
	const updateCallback = response => {
		valid(response);
		if(vm.hasError) {
			vm.hotelAndTopImgArray = HotelService.get(hotelId);
			vm.hotelAndTopImgArray.$promise.then(setHotelAndTopImg);
		}
		if(!vm.hasError) {
			vm.hasError = null;
			vm.msgSuccess='Changes saved';
		}
	}
	
	vm.updateHotel = () => {
		setNull();
		const formData = new FormData();
		const cityRegion = vm.hotel.city;
		
		vm.errorCountryMessage = false;
		vm.errorCityMessage = false;
		
		if (vm.countryIsChosen && vm.cityIsChosen) {
			vm.hotel.region = cityRegion.substring(cityRegion.lastIndexOf('(') + 1, 
					cityRegion.length - 1);
			vm.hotel.city = cityRegion.substr(0, cityRegion.lastIndexOf(' '));
		} else if (!vm.countryIsChosen && !vm.cityIsChosen) {
			vm.errorCountryMessage = true;
			vm.errorCityMessage = true;
			throw new Error('Country and City must be selected from the list.');
		} else if (!vm.countryIsChosen) {
			vm.errorCountryMessage = true;
			throw new Error('Country must be selected from the list.');
		} else if (!vm.cityIsChosen) {
			vm.errorCityMessage = true;
			throw new Error('City must be selected from the list.');
		}
		
		formData.append('idHotel', vm.hotel.id);
		formData.append('file', vm.file);
		formData.append('hotelDto', JSON.stringify(vm.hotel));
		
		HotelService.updateFileAndHotel(formData)
			.$promise
			.then(updateCallback)
			.catch(errorCallback);
	};
	
	const input = document.getElementById( 'upload' );
	$(function () {
		$('#upload').on('change', function () {
			vm.readURL();
		});
	});
	
	vm.readURL = () => {
		if (input.files && input.files[0]) {
			const reader = new FileReader();
			
			reader.onload = function (e) {
				$('#imageResult').attr('src', e.target.result);
			};	
			reader.readAsDataURL(input.files[0]);
		}
	};

	const infoArea = document.getElementById( 'upload-label' );
	input.addEventListener( 'change', showFileName );
	
	function showFileName( event ) {
		const input = event.srcElement;
		const fileName = input.files[0].name;
		infoArea.textContent = 'File name: ' + fileName;
	}
});