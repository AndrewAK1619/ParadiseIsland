angular.module('app')
    .controller('userEditController', function($routeParams, $location, $timeout, UserService, User) {
        const vm = this;
        const userId = $routeParams.userId;
        
        if(userId)
            vm.user = UserService.get(userId);
        else
            vm.user = new User();

        const saveCallback = () => {
            $location.path(`/user-edit/${vm.user.id}`);
        };
        const errorCallback = err => {
            vm.msg=`Błąd zapisu: ${err.data.message}`;
        };

        vm.saveUser = () => {
            UserService.save(vm.user)
                .then(saveCallback)
                .catch(errorCallback);
        };

        const updateCallback = response => vm.msg='Zapisano zmiany';
        vm.updateUser = () => {
            UserService.update(vm.user)
                .then(updateCallback)
                .catch(errorCallback);
        };
    });