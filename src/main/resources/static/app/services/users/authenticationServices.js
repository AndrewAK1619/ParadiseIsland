angular.module('app')
.constant('LOGIN_ENDPOINT', '/account/login')
.constant('LOGOUT_ENDPOINT', '/logout')
.service('AuthenticationService', function($http, LOGIN_ENDPOINT, LOGOUT_ENDPOINT) {
	this.authenticate = function(credentials, successCallback) {
		var authHeader = {Authorization: 'Basic ' + btoa(credentials.username+':'+credentials.password)};
		var config = {headers: authHeader};
		$http
		.post(LOGIN_ENDPOINT, {}, config)
		.then(function success(response) {
			successCallback(response);
		}, function error(reason) {
			console.log('Login error');
			console.log(reason);
		});
	}
	this.logout = function(successCallback) {
		$http.post(LOGOUT_ENDPOINT)
		.then(successCallback());
	}
});