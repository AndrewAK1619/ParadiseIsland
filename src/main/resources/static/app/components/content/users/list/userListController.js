angular.module('app')
.controller('UserListController', function($location, $state, UserService) {
	const vm = this;
	vm.users = UserService.getAll();

	vm.search = lastName => {
		vm.users = UserService.getAll({lastName});
	};

	const errorCallback = err => {
		vm.msg=`${err.data.message}`;
	};

	const deleteCallback = () => {
		$state.reload();
	};

	vm.deleteUser = (user) => {
		UserService.deleteUser(user)
			.then(deleteCallback)
			.catch(errorCallback);
	};
	vm.redirectToUserEdit = userId => {
		$location.path(`/admin/user-edit/${userId}`);
	};
	vm.redirectToUserReservations = userId => {
		$location.path(`/admin/user/reservations/${userId}`);
	};
});