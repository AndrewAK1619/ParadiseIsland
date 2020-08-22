angular.module('app')
.controller('AirlineListController', function($location, $state, AirlineService) {
	const vm = this;
	vm.airlines = AirlineService.getAll();

	vm.confirmMsg = 'Are you sure to delete this airline ? \n \n' +
		'This can lead to serious consequences, \n' +
		'for instance, delete existing bookings.';

	vm.search = airlineName => {
		vm.airlines = AirlineService.getAll({airlineName});
	};

	const errorCallback = err => {
		vm.msg=`${err.data.message}`;
	};

	const deleteCallback = () => {
		$state.reload();
	}

	vm.deleteAirline = (airline) => {
		AirlineService.deleteAirline(airline)
			.then(deleteCallback)
			.catch(errorCallback);
	};
	vm.redirectToAirlineEdit = airlineId => {
		$location.path(`/admin/airline-edit/${airlineId}`);
	};
});