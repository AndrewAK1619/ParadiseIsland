angular.module('app')
.controller('HotelEditController', function($routeParams, $location, $timeout, HotelService, Hotel) {
	const vm = this;
	const hotelId = $routeParams.hotelId;
	
	const setHotelAndTopImg = result => {
		vm.hotelArray = result.hotel;
		vm.fileArray = result.file;
		vm.hotel = vm.hotelArray[0];
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
	}
	
	const saveCallback = response => {
		valid(response);
		if(!vm.hasError) {
			vm.hasError = null;
			vm.hotel.id = response.id;
			$location.path(`/hotels-edit/${vm.hotel.id}`);
		}
	};
	const errorCallback = err => {
		vm.msg=`Data write error: ${err.data.message}`;
	};
	
	vm.file;
	vm.saveHotel = () => {
		setNull();
		const maxFileSize = '3145728';
		if(vm.file.size > maxFileSize) {
			vm.msg='Data write error: maximum file size is 3MB';
		} else {
			const formData = new FormData();
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
			vm.msg='Changes saved';
		}
	}
	
	vm.updateHotel = () => {
		setNull();
		const formData = new FormData();
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