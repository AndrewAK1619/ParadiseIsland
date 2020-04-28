angular.module('app')
.constant('DESTINATION_ENDPOINT', '/destinations/:id')
.factory('Destination', function($resource, DESTINATION_ENDPOINT) {
	return $resource(DESTINATION_ENDPOINT, { id: '@_id' }, {
		getTop12: {
			method: 'GET',
			transformRequest: angular.identity,
			headers: {'Content-type': undefined}
		}
	});
})
.service('DestinationService', function (Destination) {
	this.getTen = () => Destination.getTop12();
});