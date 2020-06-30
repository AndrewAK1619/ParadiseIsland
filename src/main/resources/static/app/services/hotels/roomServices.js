angular.module('app')
.constant('ROOM_ENDPOINT', '/admin/hotels/rooms/:id')
.constant('HOTEL_ROOM_ENDPOINT', '/admin/hotels/:hotelId/rooms/:id')
.constant('DEFAULT_ROOM_IMAGE', '/admin/hotels/rooms/defaultImg')
.constant('ALL_AVAILABLE_ROOM', '/search-result/details/:hotelId/rooms')
.factory('Room', function($resource, ROOM_ENDPOINT, HOTEL_ROOM_ENDPOINT, DEFAULT_ROOM_IMAGE, ALL_AVAILABLE_ROOM) {
	return $resource(ROOM_ENDPOINT, { id: '@_id' }, {
		uploadFileAndRoom: {
			method: 'POST',
			transformRequest: angular.identity,
			headers: {'Content-Type': undefined}
		},
		updateFileAndRoom: {
			method: 'PUT',
			transformRequest: angular.identity,
			headers: {'Content-type': undefined}
		},
		getAllRoomsAndMainImg: {
			method: 'GET',
			transformRequest: angular.identity,
			headers: {'Content-type': undefined},
			url: HOTEL_ROOM_ENDPOINT
		},
		getDefaultImage: {
			method: 'GET',
			url: DEFAULT_ROOM_IMAGE
		},
		getAvailableRooms: {
			method: 'GET',
			transformRequest: angular.identity,
			headers: {'Content-type': undefined},
			url: ALL_AVAILABLE_ROOM
		}
	});
})
.service('RoomService', function (Room) {
	this.get = index => Room.get({id: index});
	this.getAllRoomsAndMainImg = (hotelId, roomCategoryName) => Room.getAllRoomsAndMainImg({hotelId: hotelId, roomCategoryName});
	this.uploadFileAndRoom = formData => Room.uploadFileAndRoom(formData);
	this.updateFileAndRoom = formData => Room.updateFileAndRoom({id: formData.getAll('idRoom')[0]}, formData);
	this.getDefaultImage = () => Room.getDefaultImage();
	this.getAvailableRooms = (hotelId, roomCategoryName) => Room.getAvailableRooms({hotelId: hotelId, roomCategoryName});
});