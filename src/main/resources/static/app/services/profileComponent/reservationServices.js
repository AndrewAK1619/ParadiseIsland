angular.module('app')
.constant('RESERVATION_ENDPOINT', '/account/profile/reservations')
.factory('Reservation', function ($resource, RESERVATION_ENDPOINT) {
	return $resource(RESERVATION_ENDPOINT, {}, {

	});
})
.service('ReservationService', function (Reservation) {
	this.getAllBasicInf = () => Reservation.query();
});