angular.module('app')
.controller('RoomListController', function($stateParams, $location, $state, 
		RoomService, SearchDetailsService) {
	const vm = this;
	
	vm.hotelId = $stateParams.hotelId;
	
	const setRoomAndTopImg = result => {
		vm.roomArray = result.roomList;
		vm.fileArray = result.fileList;
		vm.room = vm.roomArray[0];
		vm.file = vm.fileArray[0];

		var oneImg;
		for(oneImg in vm.file) {
			vm.room[oneImg].imgSrc = vm.file[oneImg];
		}
	}
	
	var url = $location.url();
	
	if(url === `/admin/hotels/${vm.hotelId}/rooms`)
		vm.roomsAndTopImgArray = RoomService.getAllRoomsAndMainImg(vm.hotelId);
	else {
		vm.roomsAndTopImgArray = RoomService.getAvailableRooms(vm.hotelId);
		vm.isSearchUrl = true;
	}
	vm.roomsAndTopImgArray.$promise.then(setRoomAndTopImg);
	
	vm.search = roomCategoryName => {
		if(url === `/admin/hotels/${vm.hotelId}/rooms`)
			vm.roomsAndTopImgArray = RoomService.getAllRoomsAndMainImg(vm.hotelId, roomCategoryName);
		else
			vm.roomsAndTopImgArray = RoomService.getAvailableRooms(vm.hotelId, roomCategoryName);
		vm.roomsAndTopImgArray.$promise.then(setRoomAndTopImg);
	};
	
	vm.setRoom = room => {
		SearchDetailsService.setRoomAndHotelId(room, vm.hotelId);
		$location.path(`/search-result/details/${vm.hotelId}`);
	};
	
	vm.confirmMsg = 'Are you sure to delete this room ? \n \n' +
		'This can lead to serious consequences, \n' +
		'for instance, delete existing bookings.';

	const errorCallback = err => {
		vm.msg=`${err.data.message}`;
	};

	const deleteCallback = () => {
		$state.reload();
	}

	vm.deleteRoom = (room) => {
		RoomService.deleteRoom(room)
			.$promise
			.then(deleteCallback)
			.catch(errorCallback);
	};
	vm.redirectToRoomEdit = roomId => {
		$location.path(`/admin/hotels/rooms-edit/${roomId}`);
	};
});
