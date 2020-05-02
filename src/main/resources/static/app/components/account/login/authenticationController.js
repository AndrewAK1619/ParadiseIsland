angular.module('app')
.controller('AuthenticationController', function($rootScope, $location, AuthenticationService) {
	var vm = this;
	vm.credentials = {};
	var loginSuccess = response => {
		const role = response.data.authorities[0].authority;
		if(role === 'ROLE_ADMIN') {
			$rootScope.authenticated = true;
			$rootScope.authenticatedAdmin = true;
			$rootScope.authenticatedUser = false;
		} else if (role === 'ROLE_USER') {
			$rootScope.authenticated = true;
			$rootScope.authenticatedUser = true;
			$rootScope.authenticatedAdmin = false;
		}
		$location.path('/');
	}
	vm.login = () => {
		AuthenticationService.authenticate(vm.credentials, loginSuccess);
	}
	var logoutSuccess = function() {
		$rootScope.authenticated = false;
		$rootScope.authenticatedAdmin = false;
		$rootScope.authenticatedUser = false;
		$location.path('/');
	}
	$rootScope.logout = function() {
		AuthenticationService.logout(logoutSuccess);
	}
});
