angular.module('app')
    .controller('RoomEditController', function($routeParams, $location, $timeout, RoomService, RoomCategoryService, Room) {
        const vm = this;
        const roomId = $routeParams.roomId;
        
        if(roomId)
            vm.room = RoomService.get(roomId);
        else
            vm.room = new Room();
        
        vm.roomCategoriesNames = RoomCategoryService.getAllNames();

        const saveCallback = () => {
            $location.path(`/hotels/rooms-edit/${vm.room.id}`);
        };
        const errorCallback = err => {
            vm.msg=`Data write error: ${err.data.message}`;
        };

        vm.saveRoom = () => {
        	RoomService.save(vm.room)
                .then(saveCallback)
                .catch(errorCallback);
        };

        const updateCallback = response => vm.msg='Changes saved';
        
        vm.updateRoom = () => {
        	RoomService.update(vm.room)
                .then(updateCallback)
                .catch(errorCallback);
        };
    });