angular.module('app')
.controller('RoomCategoryEditController', function($routeParams, $location, $timeout, RoomCategoryService, RoomCategory) {
	const vm = this;
	const roomCategoryId = $routeParams.roomCategoryId;
	
	if(roomCategoryId)
		vm.roomCategory = RoomCategoryService.get(roomCategoryId);
	else
		vm.roomCategory = new RoomCategory();
	
	const valid = response => {
		vm.hasError = false;
		vm.fields = response.fields;
		vm.messages = response.messages;
		
		if(vm.fields) {
			vm.hasError = true;
			for(var i = 0; i < vm.fields.length; i++) {
				if(vm.fields[i] === 'name') {
					vm.errName = vm.fields[i];
					vm.errNameMsg = vm.messages[i];
				}
			}
		}
	}
	
	const setNull = () => {
		vm.roomCategory.fields = null;
		vm.roomCategory.messages = null;
		vm.errName = null;
		vm.errNameMsg = null;
	}
	
	const saveCallback = response => {
		valid(response);
		if(!vm.hasError) {
			vm.hasError = null;
			$location.path(`/hotels/rooms/categories-edit/${vm.roomCategory.id}`);
		}
	};
	const errorCallback = err => {
		vm.msg=`Data write error: ${err.data.message}`;
	};
	
	vm.saveRoomCategory = () => {
		setNull();
		RoomCategoryService.save(vm.roomCategory)
			.then(saveCallback)
			.catch(errorCallback);
	};
	
	const updateCallback = response => {
		valid(response);
		if(vm.hasError) {
			vm.roomCategory = RoomCategoryService.get(roomCategoryId);
		}
		if(!vm.hasError) {
			vm.hasError = null;
			vm.msg='Changes saved';
		}
	}
	
	vm.updateRoomCategory = () => {
		setNull();
		RoomCategoryService.update(vm.roomCategory)
			.then(updateCallback)
			.catch(errorCallback);
	};
});