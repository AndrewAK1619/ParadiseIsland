angular.module('app')
.config(function ($routeProvider, $httpProvider) {
	$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
	$routeProvider
		.when('/account/login', {
			templateUrl: 'app/components/account/login/login.html',
			controller: 'AuthenticationController',
			controllerAs: 'authController'
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
			controller: 'UserEditController',
			controllerAs: 'ctrl'
		})
		.when('/user-add', {
			templateUrl: 'app/components/users/edit/userEdit.html',
			controller: 'UserEditController',
			controllerAs: 'ctrl'
		})
		.when('/hotels', {
			templateUrl: 'app/components/offer/hotel/list/hotelList.html',
			controller: 'HotelListController',
			controllerAs: 'ctrl'
		})
		.when('/hotels/page/:pageNumber', {
			templateUrl: 'app/components/offer/hotel/list/hotelList.html',
			controller: 'HotelListController',
			controllerAs: 'ctrl'
		})
		.when('/hotels-add', {
			templateUrl: 'app/components/offer/hotel/edit/hotelEdit.html',
			controller: 'HotelEditController',
			controllerAs: 'ctrl'
		})
		.when('/hotels-edit/:hotelId', {
			templateUrl: 'app/components/offer/hotel/edit/hotelEdit.html',
			controller: 'HotelEditController',
			controllerAs: 'ctrl'
		})
		.when('/hotels/:hotelId/rooms', {
			templateUrl: 'app/components/offer/hotel/room/list/roomList.html',
			controller: 'RoomListController',
			controllerAs: 'ctrl'
		})
		.when('/hotels/:hotelId/rooms-add', {
			templateUrl: 'app/components/offer/hotel/room/edit/roomEdit.html',
			controller: 'RoomEditController',
			controllerAs: 'ctrl'
		})
		.when('/hotels/rooms-edit/:roomId', {
			templateUrl: 'app/components/offer/hotel/room/edit/roomEdit.html',
			controller: 'RoomEditController',
			controllerAs: 'ctrl'
		})
		.when('/hotels/rooms/categories', {
			templateUrl: 'app/components/offer/hotel/room/category/list/roomCategoryList.html',
			controller: 'RoomCategoryListController',
			controllerAs: 'ctrl'
		})
		.when('/hotels/rooms/categories-add', {
			templateUrl: 'app/components/offer/hotel/room/category/edit/roomCategoryEdit.html',
			controller: 'RoomCategoryEditController',
			controllerAs: 'ctrl'
		})
		.when('/hotels/rooms/categories-edit/:roomCategoryId', {
			templateUrl: 'app/components/offer/hotel/room/category/edit/roomCategoryEdit.html',
			controller: 'RoomCategoryEditController',
			controllerAs: 'ctrl'
		})
		.when('/destinations/popular', {
			templateUrl: 'app/components/offer/location/destination/list/destinationList.html',
			controller: 'DestinationListController',
			controllerAs: 'ctrl'
		})
		.when('/destinations/random', {
			templateUrl: 'app/components/offer/location/destination/list/destinationList.html',
			controller: 'DestinationListController',
			controllerAs: 'ctrl'
		})
		.when('/airlines', {
			templateUrl: 'app/components/offer/transport/airline/list/airlineList.html',
			controller: 'AirlineListController',
			controllerAs: 'ctrl'
		})
		.when('/airline-edit/:airlineId', {
			templateUrl: 'app/components/offer/transport/airline/edit/airlineEdit.html',
			controller: 'AirlineEditController',
			controllerAs: 'ctrl'
		})
		.when('/airline-add', {
			templateUrl: 'app/components/offer/transport/airline/edit/airlineEdit.html',
			controller: 'AirlineEditController',
			controllerAs: 'ctrl'
		})
		.when('/search-result', {
			templateUrl: 'app/components/offer/search/searchList.html',
			controller: 'SearchListController',
			controllerAs: 'ctrl'
		})
		.when('/search-result/page/:pageNumber', {
			templateUrl: 'app/components/offer/search/searchList.html',
			controller: 'SearchListController',
			controllerAs: 'ctrl'
		})
		.when('/search-result/details/:hotelId', {
			templateUrl: 'app/components/offer/search/details/searchDetails.html',
			controller: 'SearchDetailsController',
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
})
.run(function($window, $http, $rootScope, $location) {
	$rootScope.redirectToHomePage = () => {
		$location.path('/')
	}
	
	$rootScope.redirectToLoginPage = () => {
		$location.path('/account/login')
	}
	
	$rootScope.logout = function() {
		delete $http.defaults.headers.common['Authorization'];
		$window.localStorage.removeItem('jwt')
		$window.localStorage.removeItem('role')
		$rootScope.authenticated = false;
		$rootScope.authenticatedAdmin = false;
		$rootScope.authenticatedUser = false;
		$location.path('/');
	}
	
	const jwt = $window.localStorage.getItem('jwt');
	if(jwt) {
		$http.defaults.headers.common['Authorization'] = 'Bearer ' + jwt;
	}
	
	const role = $window.localStorage.getItem('role');
	if(role) {
		if(role === 'ROLE_ADMIN') {
			$rootScope.authenticated = true;
			$rootScope.authenticatedAdmin = true;
			$rootScope.authenticatedUser = false;
		} else if (role === 'ROLE_USER') {
			$rootScope.authenticated = true;
			$rootScope.authenticatedUser = true;
			$rootScope.authenticatedAdmin = false;
		} else {
			$rootScope.authenticated = false;
			$rootScope.authenticatedAdmin = false;
			$rootScope.authenticatedUser = false;
		}
	}
});
