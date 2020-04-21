angular.module('app')
.constant('ROOM_ENDPOINT', '/hotels/rooms/:id')
.constant('HOTEL_ROOM_ENDPOINT', '/hotels/:hotelId/rooms/:id')
.constant('DEFAULT_ROOM_IMAGE', '/hotels/rooms/defaultImg')
.factory('Room', function($resource, ROOM_ENDPOINT, HOTEL_ROOM_ENDPOINT, DEFAULT_ROOM_IMAGE) {
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
		}
	});
})
.service('RoomService', function (Room) {
	this.get = index => Room.get({id: index});
	this.getAllRoomsAndMainImg = (hotelId, roomCategoryName) => Room.getAllRoomsAndMainImg({hotelId: hotelId, roomCategoryName});
	this.uploadFileAndRoom = formData => Room.uploadFileAndRoom(formData);
	this.updateFileAndRoom = formData => Room.updateFileAndRoom({id: formData.getAll('idRoom')[0]}, formData);
	this.getDefaultImage = () => Room.getDefaultImage();
});