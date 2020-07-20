angular.module('app')
.controller('UserListController', function(UserService) {
	const vm = this;
	vm.users = UserService.getAll();

	vm.search = lastName => {
		vm.users = UserService.getAll({lastName});
	};
});