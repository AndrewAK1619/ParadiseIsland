angular.module('app')
.constant('ROOM_ENDPOINT', '/hotels/rooms/:id')
.factory('Room', function($resource, ROOM_ENDPOINT) {
    return $resource(ROOM_ENDPOINT, { id: '@_id' }, {
        update: {
            method: 'PUT'
        }
    });
})
.service('RoomService', function(Room) {
    this.getAll = params => Room.query(params);
    this.get = index => Room.get({id: index});
    this.save = Room => Room.$save();
    this.update = Room => Room.$update({id: Room.id})
});