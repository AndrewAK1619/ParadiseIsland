angular.module('app')
.constant('SEARCH_DETAILS_ENDPOINT', '/search-result/details/:hotelId')
.constant('BOOK_TRIP_ENDPOINT', '/search-result/details/:hotelId/bookTrip')
.factory('SearchDetails', function ($resource, SEARCH_DETAILS_ENDPOINT, BOOK_TRIP_ENDPOINT) {
	return $resource(SEARCH_DETAILS_ENDPOINT, { hotelId: '@_hotelId' }, {
		getSearchDetails: {
			method: 'GET',
			params: { hotelId: '@_hotelId' },
			transformRequest: angular.identity,
			headers: {'Content-type': undefined}
		},
		bookTripOffer: {
			method: 'POST',
			url: BOOK_TRIP_ENDPOINT,
			transformRequest: angular.identity,
			headers: {'Content-type': undefined}
		}
	});
})
.factory('ChosenRoom', function() {
	var room;
	var hotelId;
	
	var setRoomAndHotelId = (newRoom, hotId) => {
		room = newRoom;
		hotelId = hotId;
	};
	var getRoom = function(){
		return room;
	};
	var getHotelIdByChosenRoom = function(){
		return hotelId;
	};
	return {
		setRoomAndHotelId: setRoomAndHotelId,
		getRoom: getRoom,
		getHotelIdByChosenRoom: getHotelIdByChosenRoom
	};
})
.factory('ChosenAirlineOffer', function() {
	var airlineOffer;
	var hotelId;
	
	var setAirlineOfferAndHotelId = (newObj, hotId) => {
		airlineOffer = newObj;
		hotelId = hotId;
	};
	var getAirlineOffer = function(){
		return airlineOffer;
	};
	var getHotelIdByChosenAirlineOffer = function(){
		return hotelId;
	};
	return {
		setAirlineOfferAndHotelId: setAirlineOfferAndHotelId,
		getAirlineOffer: getAirlineOffer,
		getHotelIdByChosenAirlineOffer: getHotelIdByChosenAirlineOffer
	};
})
.service('SearchDetailsService', function (SearchDetails, ChosenRoom, ChosenAirlineOffer) {
	this.getSearchDetails = hotelId => SearchDetails.getSearchDetails({hotelId: hotelId});
	this.setRoomAndHotelId = (room, hotId) => ChosenRoom.setRoomAndHotelId(room, hotId);
	this.setAirlineOfferAndHotelId = (airlineOffer, hotId) => 
		ChosenAirlineOffer.setAirlineOfferAndHotelId(airlineOffer, hotId);
	this.getRoom = () => ChosenRoom.getRoom();
	this.getAirlineOffer = () => ChosenAirlineOffer.getAirlineOffer();
	this.getHotelIdByChosenRoom = () => ChosenRoom.getHotelIdByChosenRoom();
	this.getHotelIdByChosenAirlineOffer = () => ChosenAirlineOffer.getHotelIdByChosenAirlineOffer();
	this.bookTripOffer = (hotelId, formData) => SearchDetails.bookTripOffer({hotelId: hotelId}, formData);
});