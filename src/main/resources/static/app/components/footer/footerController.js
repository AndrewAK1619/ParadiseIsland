angular.module('app')
.controller('FooterController', function() {
	const vm = this;
	
	var date = new Date();
	vm.year = date.getFullYear();
});
