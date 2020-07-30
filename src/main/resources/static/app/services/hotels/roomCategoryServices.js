angular.module('app')
.constant('ROOM_CATEGORY_ENDPOINT', '/admin/hotels/rooms/categories/:id')
.constant('ROOM_CATEGORY_NAMES_ENDPOINT', '/admin/hotels/rooms/categories/names')
.factory('RoomCategory', function($resource, ROOM_CATEGORY_ENDPOINT) {
	return $resource(ROOM_CATEGORY_ENDPOINT, { id: '@_id' }, {
		update: {
			method: 'PUT'
		}
	});
})
.service('RoomCategoryService', function($resource, RoomCategory, ROOM_CATEGORY_NAMES_ENDPOINT) {
	this.getAll = params => RoomCategory.query(params);
	this.get = index => RoomCategory.get({id: index});
	this.save = RoomCategory => RoomCategory.$save();
	this.update = RoomCategory => RoomCategory.$update({id: RoomCategory.id})
	this.getAllNames = () => $resource(ROOM_CATEGORY_NAMES_ENDPOINT).query();
	this.deleteRoomCategory = roomCategory => roomCategory.$delete({id: roomCategory.id});
});