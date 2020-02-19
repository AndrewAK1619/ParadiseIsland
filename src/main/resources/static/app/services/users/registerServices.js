angular.module('app')
.constant('REGISTER_ENDPOINT', '/account/register')
.constant('REGISTER_CHECK', '/account/register/success/:id')
.factory('UserRegister', function($resource, REGISTER_ENDPOINT, REGISTER_CHECK) {
    return $resource(REGISTER_ENDPOINT, { id: '@_id' }, {
        getCheck: {
            method: 'GET',
            url: REGISTER_CHECK,
            params: {id: '@id'}
        }
    });
})
.service('RegisterService', function(UserRegister) {
    this.get = index => UserRegister.get({id: index});
    this.getCheck = index => UserRegister.getCheck({id: index})
    this.save = userRegister => userRegister.$save();
});