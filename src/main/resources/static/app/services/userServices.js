angular.module('app')
.constant('USER_ENDPOINT', '/users')
.factory('User', function($resource, USER_ENDPOINT) {
    return $resource(USER_ENDPOINT, { id: '@_id' });
})
.service('UserService', function(User) {
    this.getAll = params => User.query(params);
    this.get = index => User.get({id: index});
    this.save = user => user.$save();
});