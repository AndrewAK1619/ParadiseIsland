angular.module('app')
.constant('SEARCH_DETAILS_ENDPOINT', '/search-result/details/:hotelId')
.factory('SearchDetails', function ($resource, SEARCH_DETAILS_ENDPOINT) {
	return $resource(SEARCH_DETAILS_ENDPOINT, {}, {
		getSearchDetails: {
			method: 'GET',
			params: { hotelId: '@_hotelId' },
			transformRequest: angular.identity,
			headers: {'Content-type': undefined}
		},
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
});