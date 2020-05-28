angular.module('app')
.controller('HotelListController', function ($routeParams, $location, $rootScope, HotelService) {
	const vm = this;
	vm.hotelDataLoaded = false;
	vm.pageNumber = $routeParams.pageNumber;
	
	const setHotlAndTopImgData = result => {
		vm.fileArray = result.fileList[0];
		vm.pageInfo = result.hotelList[0];
		vm.hotelArray = vm.pageInfo.content;
		var oneImg;
		for(oneImg in vm.fileArray) {
			vm.hotelArray[oneImg].imgSrc = vm.fileArray[oneImg];
		}
	}
	
	const removeClassButtons = () => {
		angular.element(document.querySelector("#firstButton")).removeClass("active");
		angular.element(document.querySelector("#secondButton")).removeClass("active");
		angular.element(document.querySelector("#thirdButton")).removeClass("active");
		angular.element(document.querySelector("#fourthButton")).removeClass("active");
		angular.element(document.querySelector("#fifthButton")).removeClass("active");
	}
	
	const setNumberButtons = pageNum => {
		removeClassButtons();
		if(pageNum === 1 || pageNum === 2 || pageNum === 3) {
			vm.numButton = 3;
			if(pageNum === 1) {
				angular.element(document.querySelector("#firstButton")).addClass("active");
			} else if(pageNum === 2) {
				angular.element(document.querySelector("#secondButton")).addClass("active");
			} else if(pageNum === 3) {
				angular.element(document.querySelector("#thirdButton")).addClass("active");
			}
		} else if(pageNum === vm.totalPages - 2 || pageNum === vm.totalPages - 1 || pageNum === vm.totalPages) {
			vm.numButton = vm.totalPages - 2;
			if(pageNum === vm.totalPages - 2) {
				angular.element(document.querySelector("#thirdButton")).addClass("active");
			} else if(pageNum === vm.totalPages - 1) {
				angular.element(document.querySelector("#fourthButton")).addClass("active");
			} else if(pageNum === vm.totalPages) {
				angular.element(document.querySelector("#fifthButton")).addClass("active");
			}
		} else {
			vm.numButton = vm.pageNumber;
			angular.element(document.querySelector("#thirdButton")).addClass("active");
		}
	}
	
	const setHotelAndTopImg = result => {
		vm.hotelDataLoaded = false;
		vm.pageNumber = result.hotelList[0].number + 1;
		vm.totalPages = result.hotelList[0].totalPages;
		
		if($location.path() === '/hotels') {
			setHotlAndTopImgData(result);
			setNumberButtons(vm.pageNumber);
			vm.hotelDataLoaded = true;
		} else if(vm.pageNumber >= 1 && vm.pageNumber <= vm.totalPages) {
			setHotlAndTopImgData(result);
			setNumberButtons(vm.pageNumber);
			vm.hotelDataLoaded = true;
		} else {
			$location.path(`/hotels`);
		}
	}

	if(vm.pageNumber) {
		if(vm.pageNumber < 1 || vm.pageNumber > vm.totalPages) {
			$location.path(`/hotels`);
		}
		vm.hotelsAndTopImgArray = HotelService.loadPage(vm.pageNumber);
		vm.hotelsAndTopImgArray.$promise.then(setHotelAndTopImg);
	} else {
		vm.hotelsAndTopImgArray = HotelService.getAllHotelsAndMainImg();
		vm.hotelsAndTopImgArray.$promise.then(setHotelAndTopImg);
	}
	
	// TODO fix search
	vm.search = hotelName => {
		vm.hotelsAndTopImgArray = HotelService.getAllHotelsAndMainImg({hotelName});
		vm.hotelsAndTopImgArray.$promise.then(setHotelAndTopImg);
	};

	vm.loadPage = buttonNumber => {
		if(buttonNumber >=1 && buttonNumber <= vm.totalPages) {
			vm.pageNumber = buttonNumber;
			$location.path(`/hotels/page/${vm.pageNumber}`);
		}
	};
	vm.firstPage = () => {
		vm.loadPage(1);
	}
	vm.previousPage = () => {
		if(vm.pageNumber > 1) {
			vm.pageNumber--;
			vm.loadPage(vm.pageNumber);
		}
	};
	vm.nextPage = () => {
		if(vm.pageNumber < vm.totalPages) {
			vm.pageNumber++;
			vm.loadPage(vm.pageNumber);
		}
	};
	vm.lastPage = () => {
		vm.loadPage(vm.totalPages);
	};
});