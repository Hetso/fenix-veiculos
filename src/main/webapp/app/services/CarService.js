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

            findAllCars: function(simpleSearch){
               return $http.get(prefixV1 + (simpleSearch ? '?simpleSearch='  + simpleSearch : '')).then(extractData)
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
                fd.append('images', data.images)

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

            findAllBrands: function(simpleSearch) {
                return $http.get(prefixV1 + '/brands' + (simpleSearch ? '?search=' + simpleSearch : '')).then(extractData)
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
            /**
             * 
             * @param {{brandId: Number, logo: String}} data 
             */
            uploadBrandLogo: function(data) {
                const fd = new FormData();

                fd.append('logo', data.logo)

                return $http.post(prefixV1 + '/' + data.brandId + '/images', fd, {
                    transformRequest: angular.identity,
                    headers: {
                        'Content-type': undefined
                    }
                }).then(extractData)
            },

            // all

            /**
             * Get brand or car image
             */
            getImage: function(fileName) {
                return $http.get(prefixV1 + '/images/' + fileName).then(extractData)
            }
        };
    });
}());