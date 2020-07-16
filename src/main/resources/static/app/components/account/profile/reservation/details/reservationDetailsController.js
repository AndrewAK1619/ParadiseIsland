angular.module('app')
.controller('ReservationDetailsController', function ($cookies, $routeParams, 
		$location, ReservationService) {
	const vm = this;
	
	vm.offerBookingId = $routeParams.offerBookingId;
	
    const differenceInDays = (dt1, dt2) => {
        var one = new Date(dt1),
            two = new Date(dt2);
        var millisecondsPerDay = 1000 * 60 * 60 * 24;
        var millisBetween = two.getTime() - one.getTime();
        var days = millisBetween / millisecondsPerDay;
    
        return Math.floor(days);
    };
	
	const setDetailsData = result => {
		vm.hotel = result.hotel[0];
		vm.imgSrc = result.mainImg[0];
		vm.room = result.room[0];
		vm.airlineOffer = result.airlineOffer[0];
		
		const offerBooking = result.offerBooking[0];
		vm.numberOfPersons = offerBooking.numberOfPersons;
		vm.totalPrice = offerBooking.totalPrice;
		vm.days = differenceInDays(vm.airlineOffer.departure, vm.airlineOffer.returnTrip);
		
		const lengh = vm.hotel.hotelAdvantageDto.length;
		vm.firstHotelAdv = vm.hotel.hotelAdvantageDto.slice(0, lengh/2);
		vm.secondHotelAdv = vm.hotel.hotelAdvantageDto.slice(lengh/2, lengh);
	}

	if(vm.offerBookingId) {
		vm.searchDetails = ReservationService.getReservationDetails(vm.offerBookingId);
		vm.searchDetails.$promise.then(setDetailsData);
	}
});