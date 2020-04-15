angular.module('app')
.controller('RoomCategoryEditController', function($routeParams, $location, $timeout, RoomCategoryService, RoomCategory) {
	const vm = this;
	const roomCategoryId = $routeParams.roomCategoryId;
	
	if(roomCategoryId)
		vm.roomCategory = RoomCategoryService.get(roomCategoryId);
	else
		vm.roomCategory = new RoomCategory();
	
	const saveCallback = () => {
		$location.path(`/hotels/rooms/categories-edit/${vm.roomCategory.id}`);
	};
	const errorCallback = err => {
		vm.msg=`Data write error: ${err.data.message}`;
	};
	
	vm.saveRoomCategory = () => {
		RoomCategoryService.save(vm.roomCategory)
			.then(saveCallback)
			.catch(errorCallback);
	};
	
	const updateCallback = response => vm.msg='Changes saved';
	
	vm.updateRoomCategory = () => {
		RoomCategoryService.update(vm.roomCategory)
			.then(updateCallback)
			.catch(errorCallback);
	};
});