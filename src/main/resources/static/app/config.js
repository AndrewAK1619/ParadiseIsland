angular.module('app')
.config(function ($routeProvider) {
    $routeProvider
        .when('/account/login', {
            templateUrl: 'app/components/account/login/login.html',
            controller: 'LoginController',
            controllerAs: 'ctrl'
        })
        .when('/account/register', {
            templateUrl: 'app/components/account/registration/register.html',
            controller: 'RegisterController',
            controllerAs: 'ctrl'
        })
        .when('/account/register/success', {
            templateUrl: 'app/components/account/registration/success.html',
            controller: 'RegisterController',
            controllerAs: 'ctrl'
        })
        .when('/account/register/success/:userId', {
            templateUrl: 'app/components/account/registration/success.html',
            controller: 'RegisterController',
            controllerAs: 'ctrl'
        })
        .when('/users', {
            templateUrl: 'app/components/users/list/userList.html',
            controller: 'UserListController',
            controllerAs: 'ctrl'
        })
        .when('/user-edit/:userId', {
            templateUrl: 'app/components/users/edit/userEdit.html',
            controller: 'userEditController',
            controllerAs: 'ctrl'
        })
        .when('/user-add', {
            templateUrl: 'app/components/users/edit/userEdit.html',
            controller: 'userEditController',
            controllerAs: 'ctrl'
        })
        .when('/', {
            templateUrl: 'app/components/home/homePage/home.html',
            controller: 'HomeController',
            controllerAs: 'ctrl'
        })
        .otherwise({
            redirectTo: '/'
        });
});