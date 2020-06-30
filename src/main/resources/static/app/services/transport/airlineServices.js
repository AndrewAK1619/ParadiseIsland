angular.module('app')
.constant('AIRLINE_ENDPOINT', '/admin/airlines/:id')
.factory('Airline', function($resource, AIRLINE_ENDPOINT) {
	return $resource(AIRLINE_ENDPOINT, { id: '@_id' }, {
		update: {
			method: 'PUT'
		}
	});
})
.service('AirlineService', function(Airline) {
	this.getAll = params => Airline.query(params);
	this.get = index => Airline.get({id: index});
	this.save = airline => airline.$save();
	this.update = airline => airline.$update({id: airline.id})
});