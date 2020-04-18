angular.module('app')
.controller('HotelListController', function (HotelService) {
	const vm = this;
	
	const setHotelAndTopImg = result => {
		vm.hotelArray = result.hotelList;
		vm.fileArray = result.fileList;
		vm.hotel = vm.hotelArray[0];
		vm.file = vm.fileArray[0];

		var oneImg;
		for(oneImg in vm.file) {
			vm.hotel[oneImg].imgSrc = vm.file[oneImg];
		}
	}
	
	vm.hotelsAndTopImgArray = HotelService.getAllHotelsAndMainImg();
	vm.hotelsAndTopImgArray.$promise.then(setHotelAndTopImg);
	
	vm.search = hotelName => {
		vm.hotelsAndTopImgArray = HotelService.getAllHotelsAndMainImg({hotelName});
		vm.hotelsAndTopImgArray.$promise.then(setHotelAndTopImg);
	};
});