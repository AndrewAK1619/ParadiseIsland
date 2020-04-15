angular.module('app')
.controller('RegisterController', function($routeParams, $location, $timeout, RegisterService, UserRegister) {
	const vm = this;
	const userId = $routeParams.userId;
	
	if(userId)
		vm.userRegister = RegisterService.getCheck(userId);
	else
		vm.userRegister = new UserRegister();
	
	const saveCallback = () => {
		$location.path(`/account/register/success/${vm.userRegister.id}`);
	};
	
	const errorCallback = err => {
		vm.msg = `Failed registration attempt: ${err.data.message}`;
	};
	
	vm.saveUserRegister = () => {
		RegisterService.save(vm.userRegister)
			.then(saveCallback)
			.catch(errorCallback);
	};
});