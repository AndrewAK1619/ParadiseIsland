angular.module('app')
.controller('ReservationListController', function(ReservationService) {
	const vm = this;
	
	vm.reservations = ReservationService.getAllBasicInf();
});