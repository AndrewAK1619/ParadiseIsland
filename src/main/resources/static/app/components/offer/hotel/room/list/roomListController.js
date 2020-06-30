angular.module('app')
.controller('RoomListController', function($routeParams, $location, RoomService, SearchDetailsService) {
	const vm = this;
	
	vm.hotelId = $routeParams.hotelId;
	
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
});
