angular.module('app')
.controller('RoomEditController', function($routeParams, $location, $timeout, RoomService, RoomCategoryService, Room) {
	const vm = this;
	
	const roomId = $routeParams.roomId;
	const hotelId = $routeParams.hotelId;
	
	const setRoomAndTopImg = result => {
		vm.roomArray = result.room;
		vm.fileArray = result.file;
		vm.room = vm.roomArray[0];
		vm.imgSrc = vm.fileArray[0];
	}
    
	const setDefaultImg = result => {
		vm.file = result.file;
		vm.imgSrc = vm.file[0];
	}
	
	if(roomId) {
		vm.roomAndTopImgArray = RoomService.get(roomId);
		vm.roomAndTopImgArray.$promise.then(setRoomAndTopImg);
	} else {
		vm.room = new Room();
		vm.room.hotelId = hotelId;
		vm.defaultImg = RoomService.getDefaultImage();
		vm.defaultImg.$promise.then(setDefaultImg);
	}
	
	vm.roomCategoriesNames = RoomCategoryService.getAllNames();
	
	const saveCallback = response => {
		vm.room.id = response.id;
		$location.path(`/hotels/rooms-edit/${vm.room.id}`);
	};
	const errorCallback = err => {
		vm.msg=`Data write error: ${err.data.message}`;
	};
	
	vm.file;
	vm.saveRoom = () => {
		const formData = new FormData();
		formData.append('file', vm.file);
		formData.append('roomDto', JSON.stringify(vm.room));
		
		RoomService.uploadFileAndRoom(formData)
			.$promise
			.then(saveCallback)
			.catch(errorCallback);
	};
	
	const updateCallback = response => vm.msg='Changes saved';
	
	vm.updateRoom = () => {
		const formData = new FormData();
		formData.append('idRoom', vm.room.id);
		formData.append('file', vm.file);
		formData.append('roomDto', JSON.stringify(vm.room));
		
		RoomService.updateFileAndRoom(formData)
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