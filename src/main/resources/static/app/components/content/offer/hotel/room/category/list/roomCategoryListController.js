angular.module('app')
.controller('RoomCategoryListController', function($state, RoomCategoryService) {
	const vm = this;
	vm.roomCategories = RoomCategoryService.getAll();

	vm.confirmMsg = 'Are you sure to delete this category ? \n \n' +
		'This can lead to serious consequences, \n' +
		'for instance, delete existing bookings.';

	vm.search = name => {
		vm.roomCategories = RoomCategoryService.getAll({name});
	};

	const errorCallback = err => {
		vm.msg=`${err.data.message}`;
	};

	const deleteCallback = () => {
		$state.reload();
	}

	vm.deleteRoomCategory = (roomCategory) => {
		RoomCategoryService.deleteRoomCategory(roomCategory)
			.then(deleteCallback)
			.catch(errorCallback);
	};
});