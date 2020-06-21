angular.module('app')
.controller('SearchDetailsController', function ($cookies, $routeParams, SearchDetailsService) {
	
	const vm = this;
	
	vm.hotelId = $routeParams.hotelId;
	
    const differenceInDays = (dt1, dt2) => {
        var one = new Date(dt1[0], dt1[1], dt1[2]),
            two = new Date(dt2[0], dt2[1], dt2[2]);
        var millisecondsPerDay = 1000 * 60 * 60 * 24;
        var millisBetween = two.getTime() - one.getTime();
        var days = millisBetween / millisecondsPerDay;
    
        return Math.floor(days);      
    };
    
	const setDetailsData = result => {
		vm.hotel = result.hotelList[0];
		vm.imgSrc = result.mainImg[0];
		
		const lengh = vm.hotel.hotelAdvantageDto.length;
		vm.firstHotelAdv = vm.hotel.hotelAdvantageDto.slice(0, lengh/2);
		vm.secondHotelAdv = vm.hotel.hotelAdvantageDto.slice(lengh/2, lengh);
		
		if(SearchDetailsService.getHotelId() === vm.hotelId){
			if(!SearchDetailsService.getRoom()){
				vm.room = result.room[0];
			} else {
				vm.room = SearchDetailsService.getRoom();
			}
		} else{
			vm.room = result.room[0];
		}
		vm.airlineOffer = result.airlineOffer[0];
		
		vm.searchCookie = JSON.parse($cookies.get('searchDataMap'));
        var dt1 = vm.searchCookie.departure.split('-'),
        dt2 = vm.searchCookie.returnDate.split('-');
        vm.days = differenceInDays(dt1, dt2);
        
        vm.totalPrice = vm.room.roomPrice * vm.days + vm.airlineOffer.flightPrice;
	}
	
	vm.searchDetails = SearchDetailsService.getSearchDetails(vm.hotelId);
	vm.searchDetails.$promise.then(setDetailsData);
});