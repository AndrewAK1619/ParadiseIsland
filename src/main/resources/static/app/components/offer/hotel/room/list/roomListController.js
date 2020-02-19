angular.module('app')
.controller('RoomListController', function(RoomService) {
    const vm = this;
    vm.room = RoomService.getAll();
    
    vm.search = roomCategoryName => {
        vm.room = RoomService.getAll({roomCategoryName});
    };
});