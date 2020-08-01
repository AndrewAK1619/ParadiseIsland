angular.module('app')
.constant('RESERVATION_ENDPOINT', '/account/profile/reservations')
.constant('ADMIN_RESERVATION_ENDPOINT', '/admin/user/reservations/:userId')
.constant('RESERVATION_DETAILS_ENDPOINT', '/account/profile/reservations/:offerBookingId')
.factory('Reservation', function ($resource, RESERVATION_ENDPOINT, RESERVATION_DETAILS_ENDPOINT, 
		ADMIN_RESERVATION_ENDPOINT) {
	return $resource(RESERVATION_ENDPOINT, { offerBookingId: '@_offerBookingId', userId: '@_userId' }, {
		getReservationDetails: {
			method: 'GET',
			url: RESERVATION_DETAILS_ENDPOINT,
			params: { offerBookingId: '@_offerBookingId' },
			transformRequest: angular.identity,
			headers: {'Content-type': undefined}
		},
		getAllByUserId: {
			method: 'GET',
			url: ADMIN_RESERVATION_ENDPOINT,
			params: { userId: '@_userId' },
			transformRequest: angular.identity,
			headers: {'Content-type': undefined},
			isArray: true
		}
	});
})
.service('ReservationService', function (Reservation) {
	this.getAllBasicInf = () => Reservation.query();
	this.getReservationDetails = offerBookingId => Reservation
		.getReservationDetails({offerBookingId: offerBookingId});
	this.getAllByUserId = userId => Reservation.getAllByUserId({userId: userId});
});