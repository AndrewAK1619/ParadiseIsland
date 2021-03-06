angular.module('app')
.constant('USER_ENDPOINT', '/admin/users/:id')
.constant('PROFILE_ENDPOINT', '/account/profile')
.constant('PROFILE_EMAIL_ENDPOINT', '/account/profile/email')
.constant('PROFILE_PASSWORD_ENDPOINT', '/account/profile/password')
.constant('PROFILE_DETAILS_ENDPOINT', '/account/profile/userDetails')
.factory('User', function($resource, USER_ENDPOINT, PROFILE_ENDPOINT, 
		PROFILE_EMAIL_ENDPOINT, PROFILE_PASSWORD_ENDPOINT, PROFILE_DETAILS_ENDPOINT) {
	return $resource(USER_ENDPOINT, { id: '@_id' }, {
		update: {
			method: 'PUT'
		},
		getUserEmail: {
			method: 'GET',
			url: PROFILE_ENDPOINT,
			transformRequest: angular.identity,
			headers: {'Content-type': undefined},
			isArray: true
		},
		saveUserEmail: {
			method: 'POST',
			url: PROFILE_EMAIL_ENDPOINT
		},
		saveUserPassword: {
			method: 'POST',
			url: PROFILE_PASSWORD_ENDPOINT,
			transformRequest: angular.identity,
			headers: {'Content-type': undefined}
		},
		getUserDetails: {
			method: 'GET',
			url: PROFILE_DETAILS_ENDPOINT,
			transformRequest: angular.identity,
			headers: {'Content-type': undefined},
		},
		updateUserDetails: {
			method: 'PUT',
			url: PROFILE_DETAILS_ENDPOINT,
		},
	});
})
.service('UserService', function(User) {
	this.getAll = params => User.query(params);
	this.get = index => User.get({id: index});
	this.save = user => user.$save();
	this.update = user => user.$update({id: user.id});
	this.deleteUser = user => user.$delete({id: user.id});
	this.getUserEmail = () => User.getUserEmail();
	this.saveUserEmail = email => User.saveUserEmail(email);
	this.saveUserPassword = formData => User.saveUserPassword(formData);
	this.getUserDetails = () => User.getUserDetails();
	this.updateUserDetails = user => User.updateUserDetails(user);
});