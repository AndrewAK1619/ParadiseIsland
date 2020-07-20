angular.module('app')
.controller('AirlineListController', function(AirlineService) {
	const vm = this;
	vm.airlines = AirlineService.getAll();

	vm.search = airlineName => {
		vm.airlines = AirlineService.getAll({airlineName});
	};
});