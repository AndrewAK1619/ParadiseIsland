angular.module('app')
.controller('UserEditController', function($routeParams, $location, $timeout, UserService, User) {
	const vm = this;
	const userId = $routeParams.userId;
    
	if(userId)
		vm.user = UserService.get(userId);
	else
		vm.user = new User();
	
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
				} else if (vm.fields[i] === 'details') {
					vm.errDetails = vm.fields[i];
					vm.errDetailsMsg = vm.messages[i];
				}
			}
		}
	}

	const saveCallback = response => {
		valid(response);
		if(!vm.hasError) {
			vm.hasError = null;
			$location.path(`/user-edit/${vm.user.id}`);
		}
	};
	
	const errorCallback = err => {
		vm.msg=`Data write error: ${err.data.message}`;
	};

	const setNull = () => {
		vm.user.fields = null;
		vm.user.messages = null;
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
		vm.errDetails = null;
		vm.errDetailsMsg = null;
	}
	
	vm.saveUser = () => {
		setNull();
		UserService.save(vm.user)
			.then(saveCallback)
			.catch(errorCallback);
	};

	const updateCallback = response => {
		valid(response);
		if(vm.hasError) {
			vm.user = UserService.get(userId);
		}
		if(!vm.hasError) {
			vm.hasError = null;
			vm.msg='Changes saved';
		}
	}
	
	vm.updateUser = () => {
		setNull();
		vm.saveUser = vm.user;
		UserService.update(vm.user)
			.then(updateCallback)
			.catch(errorCallback);
	};
});