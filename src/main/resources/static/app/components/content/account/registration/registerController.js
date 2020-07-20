angular.module('app')
.controller('RegisterController', function($stateParams, $location, $timeout, RegisterService, UserRegister) {
	const vm = this;
	const userId = $stateParams.userId;
	
	if(userId)
		vm.userRegister = RegisterService.getCheck(userId);
	else 
		vm.userRegister = new UserRegister();
	
	const valid = response => {
		vm.hasError = false;
		vm.fields = response.fields;
		vm.messages = response.messages;
		
		if(vm.fields) {
			vm.hasError = true;
			for(var i = 0; i < vm.fields.length; i++) {
				if(vm.fields[i] === 'firstName') {
					vm.errFirstName = vm.fields[i];
					vm.errFirstNameMsg = vm.messages[i];
				} else if (vm.fields[i] === 'lastName') {
					vm.errLastName = vm.fields[i];
					vm.errLastNameMsg = vm.messages[i];
				} else if (vm.fields[i] === 'mobilePhone') {
					vm.errMobilePhone = vm.fields[i];
					vm.errMobilePhoneMsg = vm.messages[i];
				} else if (vm.fields[i] === 'email') {
					vm.errEmail = vm.fields[i];
					vm.errEmailMsg = vm.messages[i];
				} else if (vm.fields[i] === 'password') {
					vm.errPassword = vm.fields[i];
					vm.errPasswordMsg = vm.messages[i];
				}
			}
		}
	}
	
	const setNull = () => {
		delete vm.userRegister.fields;
		delete vm.userRegister.messages;
		vm.errFirstName = null;
		vm.errFirstNameMsg = null;
		vm.errLastName = null;
		vm.errLastNameMsg = null;
		vm.errMobilePhone = null;
		vm.errMobilePhoneMsg = null;
		vm.errEmail = null;
		vm.errEmailMsg = null;
		vm.errPassword = null;
		vm.errPasswordMsg = null;
		vm.errConfirmPassword = null;
		vm.errConfirmPasswordMsg = null;
	}
	
	const saveCallback = response => {
		valid(response);
		if(!vm.hasError) {
			vm.hasError = null;
			$location.path(`/account/register/success/${vm.userRegister.id}`);
		}
	};
	
	const errorCallback = err => {
		vm.msg = `Failed registration attempt: ${err.data.message}`;
	};
	
	vm.confirmPassword;
	
	vm.saveUserRegister = () => {
		setNull();
		if(vm.userRegister.password === vm.confirmPassword) {
			RegisterService.save(vm.userRegister)
			.then(saveCallback)
			.catch(errorCallback);
		} else {
			vm.errConfirmPassword = true;
			vm.errConfirmPasswordMsg = 'These passwords don\'t match';
		}
	};
});