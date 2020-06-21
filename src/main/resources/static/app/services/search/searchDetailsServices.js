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
	
	var setRoomAndHotelId = (newObj, hotId) => {
		room = newObj;
		hotelId = hotId;
	};
	var getRoom = function(){
		return room;
	};
	var getHotelId = function(){
		return hotelId;
	};
	return {
		setRoomAndHotelId: setRoomAndHotelId,
		getRoom: getRoom,
		getHotelId: getHotelId
	};
})
.service('SearchDetailsService', function (SearchDetails, ChosenRoom) {
	this.getSearchDetails = hotelId => SearchDetails.getSearchDetails({hotelId: hotelId});
	this.setRoomAndHotelId = (room, hotId) => ChosenRoom.setRoomAndHotelId(room, hotId);
	this.getRoom = () => ChosenRoom.getRoom();
	this.getHotelId = () => ChosenRoom.getHotelId();
});