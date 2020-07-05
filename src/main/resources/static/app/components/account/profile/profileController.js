angular.module('app')
.controller('ProfileController', function($rootScope, UserService) {
	const vm = this;

	vm.isDesireToChangeEmail = false;
	vm.iconProfile = true;
	vm.iconProfileFocused = false;

	vm.setIconProfileFocused = () => {
		vm.iconProfile = false;
		vm.iconProfileFocused = true;
	}
	
	vm.setIconProfileUnFocused = () => {
		vm.iconProfile = true;
		vm.iconProfileFocused = false;
	}
	
	vm.focusedEmailCard = () => {
		vm.isDesireToChangeEmail = true;
	}
	
	vm.unFocusedEmailCard = () => {
		vm.isDesireToChangeEmail = false;
	}
	
	const setEmailToInput = result => {
		vm.userEmail = result[0];
	}
	const userEmail = UserService.getUserEmail();
	userEmail.$promise.then(setEmailToInput);

	const saveCallback = () => {
		$rootScope.logout();
	}
	
	const sleep = ( ms ) => {
	    const end = +(new Date()) + ms;
	    while( +(new Date()) < end ){ } 
	}
	
	const errorCallback = err => {
		vm.msg = `${err.data.message}`;
	};
	
	vm.changeEmail = () => {
		vm.msg = null;
		UserService.saveUserEmail(vm.userEmail)
			.$promise
			.then(saveCallback)
			.catch(errorCallback);
	}
});