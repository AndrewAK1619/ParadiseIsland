angular.module('app')
.controller('AirlineEditController', function($routeParams, $location, $timeout, AirlineService, Airline) {
	const vm = this;
	const airlineId = $routeParams.airlineId;
    
	if(airlineId)
		vm.airline = AirlineService.get(airlineId);
	else
		vm.airline = new Airline();
	
	const valid = response => {
		vm.hasError = false;
		vm.fields = response.fields;
		vm.messages = response.messages;
		
		if(vm.fields) {
			vm.hasError = true;
			for(var i = 0; i < vm.fields.length; i++) {
				if(vm.fields[i] === 'airlineName') {
					vm.errAirlineName = vm.fields[i];
					vm.errAirlineNameMsg = vm.messages[i];
				} else if (vm.fields[i] === 'details') {
					vm.errDetails = vm.fields[i];
					vm.errDetailsMsg = vm.messages[i];
				} 
			}
		}
	}
	
	const setNull = () => {
		delete vm.airline.fields;
		delete vm.airline.messages;
		vm.errAirlineName = null;
		vm.errAirlineNameMsg = null;
		vm.errDetails = null;
		vm.errDetailsMsg = null;
	}

	const saveCallback = response => {
		valid(response);
		if(!vm.hasError) {
			vm.hasError = null;
			$location.path(`/admin/airline-edit/${vm.airline.id}`);
		}
	};
	
	const errorCallback = err => {
		vm.msg=`Data write error: ${err.data.message}`;
	};
	
	vm.saveAirline = () => {
		setNull();
		AirlineService.save(vm.airline)
			.then(saveCallback)
			.catch(errorCallback);
	};

	const updateCallback = response => {
		valid(response);
		if(vm.hasError) {
			vm.airline = AirlineService.get(airlineId);
		}
		if(!vm.hasError) {
			vm.hasError = null;
			vm.msg='Changes saved';
		}
	}
	
	vm.updateAirline = () => {
		setNull();
		AirlineService.update(vm.airline)
			.then(updateCallback)
			.catch(errorCallback);
	};
});