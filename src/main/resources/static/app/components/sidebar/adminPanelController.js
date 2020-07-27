angular.module('app')
.controller('AdminPanelController', function($rootScope, UserService) {
	const vm = this;

	vm.userDetails = UserService.getUserDetails();
	
	vm.init = function () {
		var script = document.createElement('script');
		script.type = 'text/javascript';
		script.src = "app/tools/sideBar.js";
		document.head.appendChild(script);
	}
});
