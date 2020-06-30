angular.module('app')
.constant('HOTEL_ENDPOINT', '/admin/hotels/:id')
.constant('HOTEL_LOAD_PAGE', '/admin/hotels/page/:pageNumber')
.constant('DEFAULT_HOTEL_IMAGE', '/admin/hotels/defaultImg')
.factory('Hotel', function ($resource, HOTEL_ENDPOINT, DEFAULT_HOTEL_IMAGE, HOTEL_LOAD_PAGE) {
	return $resource(HOTEL_ENDPOINT, { id: '@_id' }, {
		getAllHotelsAndMainImg: {
			method: 'GET',
			transformRequest: angular.identity,
			headers: {'Content-type': undefined}
		},
		uploadFileAndHotel: {
			method: 'POST',
			transformRequest: angular.identity,
			headers: {'Content-Type': undefined}
		},
		updateFileAndHotel: {
			method: 'PUT',
			transformRequest: angular.identity,
			headers: {'Content-type': undefined}
		},
		getAllHotelsByNameAndCountry: {
			method: 'GET',
			url: HOTEL_LOAD_PAGE,
			params: { pageNumber: '@_pageNumber' },
			transformRequest: angular.identity,
			headers: {'Content-type': undefined}
		},
		getDefaultImage: {
			method: 'GET',
			url: DEFAULT_HOTEL_IMAGE
		}
	});
})
.service('HotelService', function (Hotel) {
	this.get = index => Hotel.get({id: index});
	this.getAllHotelsAndMainImg = params => Hotel.getAllHotelsAndMainImg(params);
	this.getAllHotelsByNameAndCountry = (pageNumber, hotelName, countryName) => 
		Hotel.getAllHotelsByNameAndCountry({pageNumber: pageNumber, hotelName, countryName});
	this.uploadFileAndHotel = formData => Hotel.uploadFileAndHotel(formData);
	this.updateFileAndHotel = formData => Hotel.updateFileAndHotel({id: formData.getAll('idHotel')[0]}, formData);
	this.getDefaultImage = () => Hotel.getDefaultImage();
});