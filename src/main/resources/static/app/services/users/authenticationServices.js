angular.module('app')
.constant('LOGIN_ENDPOINT', '/account/login')
.factory('Authentication', function ($resource, LOGIN_ENDPOINT) {
	return $resource(LOGIN_ENDPOINT, {}, {
		authenticateee: {
			method: 'POST'
		}
	});
})
.service('AuthenticationService', function(Authentication) {
	this.authenticate = user => Authentication.authenticateee(user);
});