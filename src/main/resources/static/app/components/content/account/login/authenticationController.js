angular.module('app')
.controller('AuthenticationController', function($window, $http, $rootScope, $location, AuthenticationService) {
	var vm = this;
	vm.credentials = {};
	
	var loginSuccess = response => {
		$window.localStorage.setItem('jwt', response.jwt);
		$http.defaults.headers.common['Authorization'] = 'Bearer ' + response.jwt;
		
		const role = response.authorities[0].authority
		$window.localStorage.setItem('role', role);
		
		if(role === 'ROLE_ADMIN') {
			$rootScope.authenticated = true;
			$rootScope.authenticatedAdmin = true;
			$rootScope.authenticatedUser = false;
		} else if (role === 'ROLE_USER') {
			$rootScope.authenticated = true;
			$rootScope.authenticatedUser = true;
			$rootScope.authenticatedAdmin = false;
		} else {
			$rootScope.authenticated = false;
			$rootScope.authenticatedAdmin = false;
			$rootScope.authenticatedUser = false;
		}
		$location.path('/');
	}
	var errorCallback = err => {
		vm.msg = `${err.data.message}`;
	}
	vm.login = () => {
		AuthenticationService.authenticate(vm.credentials)
			.$promise
			.then(loginSuccess)
			.catch(errorCallback);
	}
});
