angular.module('app')
.controller('HotelAdvantageEditController', function($stateParams, $location, HotelAdvantageService, Advantage) {
	const vm = this;
	vm.hotelId = $stateParams.hotelId;
	const advantageId = $stateParams.id;

	if(advantageId)
		vm.advantage = HotelAdvantageService.get(vm.hotelId, advantageId);
	else
		vm.advantage = new Advantage();

	const saveCallback = response => {
		vm.advantage.id = response.id;
		$location.path(`/admin/hotels/${vm.hotelId}/advantages-edit/${vm.advantage.id}`);
	};
	const errorCallback = err => {
		vm.msg=`${err.data.message}`;
	};

	vm.saveAdvantage = () => {
		vm.advantage.hotelId = vm.hotelId;
		HotelAdvantageService.save(vm.advantage)
			.then(saveCallback)
			.catch(errorCallback);
	};

	const updateCallback = () => {
		vm.msgSuccess='Changes saved';
	}

	vm.updateAdvantage = () => {
		HotelAdvantageService.update(vm.advantage)
			.then(updateCallback)
			.catch(errorCallback);
	};
});