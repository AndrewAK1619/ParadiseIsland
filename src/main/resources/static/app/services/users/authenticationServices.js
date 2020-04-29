angular.module('app')
.constant('LOGIN_ENDPOINT', '/account/login')
.service('AuthenticationService', function($http, LOGIN_ENDPOINT) {
	this.authenticate = function(credentials, successCallback) {
		var authHeader = {Authorization: 'Basic ' + btoa(credentials.username+':'+credentials.password)};
		var config = {headers: authHeader};
		$http
		.post(LOGIN_ENDPOINT, {}, config)
		.then(function success(value) {
			successCallback();
		}, function error(reason) {
			console.log('Login error');
			console.log(reason);
		});
	}
});