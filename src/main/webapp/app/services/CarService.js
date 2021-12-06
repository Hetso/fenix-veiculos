(function () {
    'use strict';

    const services = angular.module('fv.services');

    const extractData = function (data) {
        return data.data;
    };

    const prefixV1 = '/api/v1/cars';

    services.factory('CarService', function ($http) {
        return {
            // cars

            findAllCars: function(status, simpleSearch){
               return $http.get(prefixV1 + `?brandStatus=${status}` + (simpleSearch ? '&simpleSearch='  + simpleSearch : '')).then(extractData)
            },
            findCarById: function(id) {
                return $http.get(prefixV1 + '/' + id).then(extractData)
            },
            saveCar: function(car) {
                if(car.id) {
                    return $http.put(prefixV1 + '/' + car.id, car).then(extractData)
                }

                return $http.post(prefixV1, car).then(extractData)
            },
            removeCar: function(car) {
                return $http.delete(prefixV1 + '/' + car.id).then(extractData)
            },
            /**
             * 
             * @param {{carId: Number, coverImage: String, images: String[]}} data 
             * @returns 
             */
            uploadCarImages: function(data) {
                const fd = new FormData();

                fd.append('coverImage', data.coverImage)
                if(data.images && data.images.length) {
                    data.images.forEach(image => {
                        fd.append('images', image)
                    })
                }

                return $http.post(prefixV1 + '/' + data.carId + '/images', fd, {
                    transformRequest: angular.identity,
                    headers: {
                        'Content-type': undefined
                    }
                }).then(extractData)
            },
            removeCarImage: function(carId, fileName) {
                return $http.delete(prefixV1 + '/' + carId + '/images/' + fileName).then(extractData)
            },

            // brands

            findAllBrands: function(status, simpleSearch) {
                return $http.get(prefixV1 + '/brands' + `?status=${status}` + (simpleSearch ? '&search=' + simpleSearch : '')).then(extractData)
            },
            findBrandById: function(id) {
                return $http.get(prefixV1 + '/brands/' + id).then(extractData)
            },
            saveBrand: function(brand) {
                if(brand.id) {
                    return $http.put(prefixV1 + '/brands/' + brand.id, brand).then(extractData)
                }

                return $http.post(prefixV1 + '/brands', brand).then(extractData)
            },
            removeBrand: function(brand) {
                return $http.delete(prefixV1 + '/brands/' + brand.id).then(extractData)
            },
            disableBrand: function(brand) {
                return $http.put(prefixV1 + '/brands/disable/' + brand.id).then(extractData)
            },
            /**
             * 
             * @param {{brandId: Number, logo: String}} data 
             */
            uploadBrandLogo: function(data) {
                const fd = new FormData();

                fd.append('logo', data.logo)

                return $http.post(prefixV1 + '/brands/' + data.brandId + '/images', fd, {
                    transformRequest: angular.identity,
                    headers: {
                        'Content-type': undefined
                    }
                }).then(extractData)
            },
            disableBrand: function(brand) {
                return $http.put(prefixV1 + '/brands/' + brand.id + '/disable').then(extractData);
            },
            enableBrand: function(brand) {
                return $http.put(prefixV1 + '/brands/' + brand.id + '/enable').then(extractData);
            },
            // all

            /**
             * Get brand or car image
             */
            getImageUrl: function(fileName) {
                return prefixV1 + '/images/' + fileName;
            }
        };
    });
}());