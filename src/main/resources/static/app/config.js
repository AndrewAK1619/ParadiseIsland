angular.module('app')
.config(function ($stateProvider, $urlRouterProvider, $httpProvider) {
	$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
	$stateProvider
		.state('myapp', {
			views: {
				'header': {
					templateUrl: 'app/components/header/header.html'
				},
				'adminPanel': {
					templateUrl: 'app/components/sidebar/adminPanel.html',
					controller: 'AdminPanelController',
					controllerAs: 'ctrl'
				},
				'userPanel': {
					templateUrl: 'app/components/sidebar/userPanel.html',
					controller: 'UserPanelController',
					controllerAs: 'ctrl'
				},
				'content': {
					template: '<div ui-view></div>'
				},
				'footer': {
					templateUrl: 'app/components/footer/footer.html',
					controller: 'FooterController',
					controllerAs: 'ctrl'
				}
			}
		})
		.state('myapp.login', {
			url: '/account/login',
			templateUrl: 'app/components/content/account/login/login.html',
			controller: 'AuthenticationController',
			controllerAs: 'authController'
		})
		.state('myapp.register', {
			url: '/account/register',
			templateUrl: 'app/components/content/account/registration/register.html',
			controller: 'RegisterController',
			controllerAs: 'ctrl'
		})
		.state('myapp.register/success', {
			url: '/account/register/success',
			templateUrl: 'app/components/content/account/registration/success.html',
			controller: 'RegisterController',
			controllerAs: 'ctrl'
		})
		.state('myapp.register/success/id', {
			url: '/account/register/success/:userId',
			templateUrl: 'app/components/content/account/registration/success.html',
			controller: 'RegisterController',
			controllerAs: 'ctrl'
		})
		.state('myapp.profile', {
			url: '/account/profile',
			templateUrl: 'app/components/content/account/profile/profile.html',
			controller: 'ProfileController',
			controllerAs: 'ctrl'
		})
		.state('myapp.profile/userDetails', {
			url: '/account/profile/userDetails',
			templateUrl: 'app/components/content/account/profile/userDetails/userDetails.html',
			controller: 'UserDetailsController',
			controllerAs: 'ctrl'
		})
		.state('myapp.reservations', {
			url: '/account/profile/reservations',
			templateUrl: 'app/components/content/account/profile/reservation/reservationList.html',
			controller: 'ReservationListController',
			controllerAs: 'ctrl'
		})
		.state('myapp.reservations/details', {
			url: '/account/profile/reservations/details/:offerBookingId',
			templateUrl: 'app/components/content/account/profile/reservation/details/reservationDetails.html',
			controller: 'ReservationDetailsController',
			controllerAs: 'ctrl'
		})
		.state('myapp.users', {
			url: '/admin/users',
			templateUrl: 'app/components/content/users/list/userList.html',
			controller: 'UserListController',
			controllerAs: 'ctrl'
		})
		.state('myapp.user-edit', {
			url: '/admin/user-edit/:userId',
			templateUrl: 'app/components/content/users/edit/userEdit.html',
			controller: 'UserEditController',
			controllerAs: 'ctrl'
		})
		.state('myapp.user-add', {
			url: '/admin/user-add',
			templateUrl: 'app/components/content/users/edit/userEdit.html',
			controller: 'UserEditController',
			controllerAs: 'ctrl'
		})
		.state('myapp.user/reservations/Id', {
			url: '/admin/user/reservations/:userId',
			templateUrl: 'app/components/content/account/profile/reservation/reservationList.html',
			controller: 'ReservationListController',
			controllerAs: 'ctrl'
		})
		.state('myapp.user/reservations/details/Id', {
			url: '/admin/user/reservations/details/:offerBookingId',
			templateUrl: 'app/components/content/account/profile/reservation/details/reservationDetails.html',
			controller: 'ReservationDetailsController',
			controllerAs: 'ctrl'
		})
		.state('myapp.hotels', {
			url: '/admin/hotels',
			templateUrl: 'app/components/content/offer/hotel/list/hotelList.html',
			controller: 'HotelListController',
			controllerAs: 'ctrl'
		})
		.state('myapp.hotels/page', {
			url: '/admin/hotels/page/:pageNumber',
			templateUrl: 'app/components/content/offer/hotel/list/hotelList.html',
			controller: 'HotelListController',
			controllerAs: 'ctrl'
		})
		.state('myapp.hotels-add', {
			url: '/admin/hotels-add',
			templateUrl: 'app/components/content/offer/hotel/edit/hotelEdit.html',
			controller: 'HotelEditController',
			controllerAs: 'ctrl'
		})
		.state('myapp.hotels-edit', {
			url: '/admin/hotels-edit/:hotelId',
			templateUrl: 'app/components/content/offer/hotel/edit/hotelEdit.html',
			controller: 'HotelEditController',
			controllerAs: 'ctrl'
		})
		.state('myapp.advantages', {
			url: '/admin/hotels/:hotelId/advantages',
			templateUrl: 'app/components/content/offer/hotel/advantage/list/advantageList.html',
			controller: 'HotelAdvantageListController',
			controllerAs: 'ctrl'
		})
		.state('myapp.advantages-add', {
			url: '/admin/hotels/:hotelId/advantages-add',
			templateUrl: 'app/components/content/offer/hotel/advantage/edit/advantageEdit.html',
			controller: 'HotelAdvantageEditController',
			controllerAs: 'ctrl'
		})
		.state('myapp.advantages-edit', {
			url: '/admin/hotels/:hotelId/advantages-edit/:id',
			templateUrl: 'app/components/content/offer/hotel/advantage/edit/advantageEdit.html',
			controller: 'HotelAdvantageEditController',
			controllerAs: 'ctrl'
		})
		.state('myapp.hotels/rooms', {
			url: '/admin/hotels/:hotelId/rooms',
			templateUrl: 'app/components/content/offer/hotel/room/list/roomList.html',
			controller: 'RoomListController',
			controllerAs: 'ctrl'
		})
		.state('myapp.rooms-add', {
			url: '/admin/hotels/:hotelId/rooms-add',
			templateUrl: 'app/components/content/offer/hotel/room/edit/roomEdit.html',
			controller: 'RoomEditController',
			controllerAs: 'ctrl'
		})
		.state('myapp.rooms-edit', {
			url: '/admin/hotels/rooms-edit/:roomId',
			templateUrl: 'app/components/content/offer/hotel/room/edit/roomEdit.html',
			controller: 'RoomEditController',
			controllerAs: 'ctrl'
		})
		.state('myapp.categories', {
			url: '/admin/hotels/rooms/categories',
			templateUrl: 'app/components/content/offer/hotel/room/category/list/roomCategoryList.html',
			controller: 'RoomCategoryListController',
			controllerAs: 'ctrl'
		})
		.state('myapp.categories-add', {
			url: '/admin/hotels/rooms/categories-add',
			templateUrl: 'app/components/content/offer/hotel/room/category/edit/roomCategoryEdit.html',
			controller: 'RoomCategoryEditController',
			controllerAs: 'ctrl'
		})
		.state('myapp.categories-edit', {
			url: '/admin/hotels/rooms/categories-edit/:roomCategoryId',
			templateUrl: 'app/components/content/offer/hotel/room/category/edit/roomCategoryEdit.html',
			controller: 'RoomCategoryEditController',
			controllerAs: 'ctrl'
		})
		.state('myapp.destinations/popular', {
			url: '/destinations/popular',
			templateUrl: 'app/components/content/offer/location/destination/list/destinationList.html',
			controller: 'DestinationListController',
			controllerAs: 'ctrl'
		})
		.state('myapp.destinations/random', {
			url: '/destinations/random',
			templateUrl: 'app/components/content/offer/location/destination/list/destinationList.html',
			controller: 'DestinationListController',
			controllerAs: 'ctrl'
		})
		.state('myapp.destination/details', {
			url: '/destinations/details/:id',
			templateUrl: 'app/components/content/offer/location/destination/list/details/destinationDetails.html',
			controller: 'DestinationDetailsController',
			controllerAs: 'ctrl'
		})
		.state('myapp.airlines', {
			url: '/admin/airlines',
			templateUrl: 'app/components/content/offer/transport/airline/list/airlineList.html',
			controller: 'AirlineListController',
			controllerAs: 'ctrl'
		})
		.state('myapp.airline-edit', {
			url: '/admin/airline-edit/:airlineId',
			templateUrl: 'app/components/content/offer/transport/airline/edit/airlineEdit.html',
			controller: 'AirlineEditController',
			controllerAs: 'ctrl'
		})
		.state('myapp.airline-add', {
			url: '/admin/airline-add',
			templateUrl: 'app/components/content/offer/transport/airline/edit/airlineEdit.html',
			controller: 'AirlineEditController',
			controllerAs: 'ctrl'
		})
		.state('myapp.search-result', {
			url: '/search-result',
			templateUrl: 'app/components/content/offer/search/searchList.html',
			controller: 'SearchListController',
			controllerAs: 'ctrl'
		})
		.state('myapp.search-result/page', {
			url: '/search-result/page/:pageNumber',
			templateUrl: 'app/components/content/offer/search/searchList.html',
			controller: 'SearchListController',
			controllerAs: 'ctrl'
		})
		.state('myapp.search-result/details', {
			url: '/search-result/details/:hotelId',
			templateUrl: 'app/components/content/offer/search/details/searchDetails.html',
			controller: 'SearchDetailsController',
			controllerAs: 'ctrl'
		})
		.state('myapp.search-result/details/rooms', {
			url: '/search-result/details/:hotelId/rooms',
			templateUrl: 'app/components/content/offer/hotel/room/list/roomList.html',
			controller: 'RoomListController',
			controllerAs: 'ctrl'
		})
		.state('myapp.search-result/details/airlines', {
			url: '/search-result/details/:hotelId/airlines',
			templateUrl: 'app/components/content/offer/transport/airlineOffer/list/airlineOfferList.html',
			controller: 'AirlineOfferListController',
			controllerAs: 'ctrl'
		})
		.state('myapp.search-result/details/success', {
			url: '/search-result/details/success',
			templateUrl: 'app/components/content/offer/search/details/success.html',
			controller: 'SearchDetailsController',
			controllerAs: 'ctrl'
		})
		.state('myapp.search-result/details/success/id', {
			url: '/search-result/details/success/:offerBookingId',
			templateUrl: 'app/components/content/offer/search/details/success.html',
			controller: 'SearchDetailsController',
			controllerAs: 'ctrl'
		})
		.state('myapp.aboutUs', {
			url: '/about_us',
			templateUrl: 'app/components/content/company/aboutUs/aboutUs.html'
		})
		.state('myapp.contactInformations', {
			url: '/conctact_informations',
			templateUrl: 'app/components/content/company/contact/contactInformation.html'
		})
		.state('myapp.homePage', {
			url: '/',
			templateUrl: 'app/components/content/home/homePage/home.html',
			controller: 'HomeController',
			controllerAs: 'ctrl'
		})
		$urlRouterProvider.otherwise("/");
})
.run(function($transitions, $window, $http, $rootScope, $location, UserService) {
    $transitions.onSuccess({}, function () {
        document.body.scrollTop = document.documentElement.scrollTop = 0;
    })
	
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
	var isOpenRunSite;
	const errorCallback = err => {
		var str = err.data.message.substring(0, 11);

		if(str === 'JWT expired') {
			$rootScope.logout();
			if(isOpenRunSite) {
				location.reload();
			}
		}
	}
	$rootScope.checkTokenExpired = (isOpenSite) => {
		isOpenRunSite = isOpenSite;
		UserService.getUserEmail()
			.$promise
			.catch(errorCallback);
	}
	$rootScope.checkTokenExpired(true);
})
.factory("$exceptionHandler", ['$injector', function($injector) {
	return function (exception, cause) {
		const $rootScope = $injector.get("$rootScope");
		$rootScope.checkTokenExpired(false);
	};
}]);
