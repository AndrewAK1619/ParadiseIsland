angular.module('app')
.controller('RoomListController', function(RoomService) {
	const vm = this;
	
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
	
	vm.roomsAndTopImgArray = RoomService.getAllRoomsAndMainImg();
	vm.roomsAndTopImgArray.$promise.then(setRoomAndTopImg);
	
	vm.search = roomCategoryName => {
		vm.roomsAndTopImgArray = RoomService.getAllRoomsAndMainImg({roomCategoryName});
		vm.roomsAndTopImgArray.$promise.then(setRoomAndTopImg);
	};
});
