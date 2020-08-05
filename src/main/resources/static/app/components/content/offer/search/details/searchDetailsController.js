angular.module('app')
.controller('SearchDetailsController', function ($cookies, $stateParams, $location, SearchDetailsService) {
	const vm = this;
	
	vm.hotelId = $stateParams.hotelId;
	vm.offerBookingId = $stateParams.offerBookingId;
	
    const differenceInDays = (dt1, dt2) => {
        var one = new Date(dt1[0], dt1[1], dt1[2]),
            two = new Date(dt2[0], dt2[1], dt2[2]);
        var millisecondsPerDay = 1000 * 60 * 60 * 24;
        var millisBetween = two.getTime() - one.getTime();
        var days = millisBetween / millisecondsPerDay;
    
        return Math.floor(days);
    };
    
	const setDetailsData = result => {
		vm.hotel = result.hotel[0];
		vm.imgSrc = result.mainImg[0];
		
		const lengh = vm.hotel.hotelAdvantageDto.length;
		vm.firstHotelAdv = vm.hotel.hotelAdvantageDto.slice(0, lengh/2);
		vm.secondHotelAdv = vm.hotel.hotelAdvantageDto.slice(lengh/2, lengh);
		
		if(SearchDetailsService.getHotelIdByChosenRoom() === vm.hotelId){
			if(!SearchDetailsService.getRoom()){
				vm.room = result.room[0];
			} else {
				vm.room = SearchDetailsService.getRoom();
			}
		} else{
			vm.room = result.room[0];
		}
		
		if(SearchDetailsService.getHotelIdByChosenAirlineOffer() === vm.hotelId){
			if(!SearchDetailsService.getAirlineOffer()){
				vm.airlineOffer = result.airlineOffer[0];
			} else {
				vm.airlineOffer = SearchDetailsService.getAirlineOffer();
			}
		} else{
			vm.airlineOffer = result.airlineOffer[0];
		}
		vm.searchCookie = JSON.parse($cookies.get('searchDataMap'));
        var dt1 = vm.searchCookie.departure.split('-'),
        dt2 = vm.searchCookie.returnDate.split('-');
        vm.days = differenceInDays(dt1, dt2);
        
        vm.hotelTotalPrice = vm.room.roomPrice * vm.days;
        vm.totalPriceForOnePerson = vm.room.roomPrice * vm.days + vm.airlineOffer.flightPrice;
	}
	
	if(!vm.offerBookingId) {
		vm.searchDetails = SearchDetailsService.getSearchDetails(vm.hotelId);
		vm.searchDetails.$promise.then(setDetailsData);
	}
	
	const saveCallback = response => {
		vm.offerBookingId = response.id;
		if(vm.offerBookingId){
			$location.path(`/search-result/details/success/${vm.offerBookingId}`);
		}
	};

	const errorCallback = err => {
		vm.msg = `Failed booking attempt: ${err.data.message}`;
	};
	
	vm.bookTrip = () => {
		const formData = new FormData();
		
		const totalPrice = vm.totalPriceForOnePerson * vm.searchCookie.persons;

		formData.append('roomId', vm.room.id);
		formData.append('hotelTotalPrice', vm.hotelTotalPrice);
		formData.append('departure', vm.searchCookie.departure);
		formData.append('returnDate', vm.searchCookie.returnDate);
		formData.append('airlineOfferId', vm.airlineOffer.id);
		formData.append('numberOfPersons', vm.searchCookie.persons);
		formData.append('totalPrice', totalPrice);
		
		SearchDetailsService.bookTripOffer(vm.hotelId, formData)
				.$promise
				.then(saveCallback)
				.catch(errorCallback);
	}
});