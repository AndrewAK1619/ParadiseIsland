angular.module('app')
.constant('ROOM_ENDPOINT', '/hotels/rooms/:id')
.constant('DEFAULT_IMAGE', '/hotels/rooms/defaultImg')
.factory('Room', function($resource, ROOM_ENDPOINT, DEFAULT_IMAGE) {
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
		getDefaultImage: {
			method: 'GET',
			url: DEFAULT_IMAGE
		},
		getAllRoomsAndMainImg: {
			method: 'GET',
			transformRequest: angular.identity,
			headers: {'Content-type': undefined},
			url: ROOM_ENDPOINT
		}
	});
})
.service('RoomService', function (Room) {
	this.getAllRoomsAndMainImg = params => Room.getAllRoomsAndMainImg(params);
	this.get = index => Room.get({id: index});
	this.uploadFileAndRoom = formData => Room.uploadFileAndRoom(formData);
	this.updateFileAndRoom = formData => Room.updateFileAndRoom({id: formData.getAll('idRoom')[0]}, formData);
	this.getDefaultImage = () => Room.getDefaultImage();
});