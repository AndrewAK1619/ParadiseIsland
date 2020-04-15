angular.module('app')
.constant('USER_ENDPOINT', '/users/:id')
.factory('User', function($resource, USER_ENDPOINT) {
	return $resource(USER_ENDPOINT, { id: '@_id' }, {
		update: {
			method: 'PUT'
		}
	});
})
.service('UserService', function(User) {
	this.getAll = params => User.query(params);
	this.get = index => User.get({id: index});
	this.save = user => user.$save();
	this.update = user => user.$update({id: user.id})
});