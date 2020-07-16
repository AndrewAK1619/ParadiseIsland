angular.module('app')
.constant('RESERVATION_ENDPOINT', '/account/profile/reservations')
.constant('RESERVATION_DETAILS_ENDPOINT', '/account/profile/reservations/:offerBookingId')
.factory('Reservation', function ($resource, RESERVATION_ENDPOINT, RESERVATION_DETAILS_ENDPOINT) {
	return $resource(RESERVATION_ENDPOINT, { offerBookingId: '@_offerBookingId' }, {
		getReservationDetails: {
			method: 'GET',
			url: RESERVATION_DETAILS_ENDPOINT,
			params: { offerBookingId: '@_offerBookingId' },
			transformRequest: angular.identity,
			headers: {'Content-type': undefined}
		},
	});
})
.service('ReservationService', function (Reservation) {
	this.getAllBasicInf = () => Reservation.query();
	this.getReservationDetails = offerBookingId => Reservation
		.getReservationDetails({offerBookingId: offerBookingId});
});