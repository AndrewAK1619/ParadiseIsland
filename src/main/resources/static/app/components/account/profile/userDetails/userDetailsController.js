angular.module('app')
.controller('UserDetailsController', function(UserService) {
	const vm = this;

	vm.user = UserService.getUserDetails();
	
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
				}
			}
		}
	}
	
	const setNull = () => {
		delete vm.user.fields;
		delete vm.user.messages;
		vm.errFirstName = null;
		vm.errFirstNameMsg = null;
		vm.errLastName = null;
		vm.errLastNameMsg = null;
		vm.errMobilePhone = null;
		vm.errMobilePhoneMsg = null;
	}
	
	const saveCallback = response => {
		valid(response);
		if(!vm.hasError) {
			vm.hasError = null;
			vm.msgSuccess = 'Changes saved';
		}
	};
	
	const errorCallback = err => {
		vm.msg=`Data write error: ${err.data.message}`;
	};

	vm.saveUser = () => {
		UserService.updateUserDetails(vm.user)
			.$promise
			.then(saveCallback)
			.catch(errorCallback);
	};
});