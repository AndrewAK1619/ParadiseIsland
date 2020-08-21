angular.module('app')
.constant('HOTEL_ADVANTAGE_ENDPOINT', '/admin/hotels/:hotelId/advantages/:id')
.factory('Advantage', function($resource, HOTEL_ADVANTAGE_ENDPOINT) {
	return $resource(HOTEL_ADVANTAGE_ENDPOINT, { id: '@_id', hotelId: '@_hotelId' }, {
		getAll: {
			method: 'GET',
			url: HOTEL_ADVANTAGE_ENDPOINT,
			transformRequest: angular.identity,
			headers: {'Content-type': undefined},
			isArray: true
		},
		update: {
			method: 'PUT'
		}
	});
})
.service('HotelAdvantageService', function($resource, Advantage) {
	this.getAll = hotId => Advantage.getAll({hotelId: hotId});
	this.get = (hotId, avdId) => Advantage.get({hotelId: hotId, id: avdId});
	this.save = Advantage => Advantage.$save();
	this.update = Advantage => Advantage.$update({id: Advantage.id})
	this.deleteAdvantage = advantage => advantage.$delete({id: advantage.id});
});