angular.module('app')
.controller('HotelAdvantageListController', function($stateParams, $state, HotelAdvantageService) {
	const vm = this;
	vm.hotelId = $stateParams.hotelId;
	
	vm.advantages = HotelAdvantageService.getAll(vm.hotelId);

	const errorCallback = err => {
		vm.msg=`${err.data.message}`;
	};

	const deleteCallback = () => {
		$state.reload();
	}

	vm.deleteAdvantage = (advantage) => {
		HotelAdvantageService.deleteAdvantage(advantage)
			.then(deleteCallback)
			.catch(errorCallback);
	};
});