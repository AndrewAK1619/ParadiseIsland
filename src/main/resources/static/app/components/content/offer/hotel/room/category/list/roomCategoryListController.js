angular.module('app')
.controller('RoomCategoryListController', function(RoomCategoryService) {
	const vm = this;
	vm.roomCategories = RoomCategoryService.getAll();
	
	vm.search = name => {
		vm.roomCategories = RoomCategoryService.getAll({name});
	};
});