angular.module('app')
.controller('AuthenticationController', function($rootScope, $location, AuthenticationService) {
	var vm = this;
	vm.credentials = {};
	var loginSuccess = function() {
		$rootScope.authenticated = true;
		$location.path('/hotels-add');
	}
	vm.login = function() {
		AuthenticationService.authenticate(vm.credentials, loginSuccess);
	}
 });