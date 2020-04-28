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
		
		
		.when('/destinations', {
			templateUrl: 'app/components/offer/location/destination/list/destinationList.html',
			controller: 'DestinationListController',
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