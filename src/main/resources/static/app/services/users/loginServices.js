angular.module('app')
.constant('LOGIN_ENDPOINT', '/api/account/login')
.factory('Login', function($resource, LOGIN_ENDPOINT) {
	return $resource(LOGIN_ENDPOINT);
})
.service('LoginService', function(Login) {

});