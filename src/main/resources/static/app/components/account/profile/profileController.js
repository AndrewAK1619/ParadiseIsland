angular.module('app')
.controller('ProfileController', function($rootScope, UserService) {
	const vm = this;

	vm.setDefaultValue = () => {
		vm.isDesireToChangeEmail = false;
		vm.iconProfileEmail = true;
		vm.iconProfileFocusedEmail = false;
		
		vm.isDesireToChangePassword = false;
		vm.iconProfileOldPassword = true;
		vm.iconProfileFocusedOldPassword = false;
		
		vm.iconProfileNewPassword = true;
		vm.iconProfileFocusedNewPassword = false;
		
		vm.iconProfileConfirmPassword = true;
		vm.iconProfileFocusedConfirmPassword = false;
	}
	
	vm.setDefaultValue();
	
	vm.setIconProfileFocusedEmail = () => {
		vm.iconProfileEmail = false;
		vm.iconProfileFocusedEmail = true;
	}
	vm.setIconProfileUnFocusedEmail = () => {
		vm.iconProfileEmail = true;
		vm.iconProfileFocusedEmail = false;
	}
	vm.focusedEmailCard = () => {
		vm.isDesireToChangeEmail = true;
	}
	vm.unFocusedEmailCard = () => {
		vm.isDesireToChangeEmail = false;
	}
	
	vm.setIconProfileFocusedOldPassword = () => {
		vm.iconProfileOldPassword = false;
		vm.iconProfileFocusedOldPassword = true;
	}
	vm.setIconProfileUnFocusedOldPassword = () => {
		vm.iconProfileOldPassword = true;
		vm.iconProfileFocusedOldPassword = false;
	}	
	vm.focusedPasswordCard = () => {
		vm.isDesireToChangePassword = true;
	}
	vm.unFocusedPasswordCard = () => {
		vm.isDesireToChangePassword = false;
	}
	
	vm.setIconProfileFocusedNewPassword = () => {
		vm.iconProfileNewPassword = false;
		vm.iconProfileFocusedNewPassword = true;
	}
	vm.setIconProfileUnFocusedNewPassword = () => {
		vm.iconProfileNewPassword = true;
		vm.iconProfileFocusedNewPassword = false;
	}	
	
	vm.setIconProfileFocusedConfirmPassword = () => {
		vm.iconProfileConfirmPassword = false;
		vm.iconProfileFocusedConfirmPassword = true;
	}
	vm.setIconProfileUnFocusedConfirmPassword = () => {
		vm.iconProfileConfirmPassword = true;
		vm.iconProfileFocusedConfirmPassword = false;
	}	
	
	const setEmailToInput = result => {
		vm.userEmail = result[0];
	}
	const userEmail = UserService.getUserEmail();
	userEmail.$promise.then(setEmailToInput);

	const saveCallbackEmail = () => {
		$rootScope.logout();
	}
	const errorCallbackEmail = err => {
		vm.msgEmail = `${err.data.message}`;
	};
	
	vm.changeEmail = () => {
		vm.msgEmail = null;
		UserService.saveUserEmail(vm.userEmail)
			.$promise
			.then(saveCallbackEmail)
			.catch(errorCallbackEmail);
	}
	
	vm.changePassword = () => {
		vm.msgPassword = null;
		UserService.saveUserPassword(vm.oldPassword, vm.newPassword)
			.$promise
			.then(saveCallbackPassword)
			.catch(errorCallbackPassword);
	}
});