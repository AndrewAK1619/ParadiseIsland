angular.module('app')
.controller('ReservationListController', function($stateParams, ReservationService) {
	const vm = this;
	
	const userId = $stateParams.userId;
	
	const setReservationsData = result => {
		vm.isAdminData = true;
		vm.reservations = result;
	}
	
	if(userId)
		ReservationService.getAllByUserId(userId).$promise.then(setReservationsData);
	else
		vm.reservations = ReservationService.getAllBasicInf();
});