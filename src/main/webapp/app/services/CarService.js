(function () {
    'use strict';

    const services = angular.module('fv.services');

    const extractData = function (data) {
        return data.data;
    };

    const prefixV1 = '/api/v1';

    services.factory('CarService', function ($http) {
        return {
            findAllCars: function(){
               return $http.get(prefixV1 + '/cars').then(extractData)
            },
            saveCar: function(car) {
                if(car.id) {
                    return $http.put(prefixV1 + '/cars/' + car.id, car).then(extractData)
                }

                return $http.post(prefixV1 + '/cars', car).then(extractData)
            },
            removeCar: function(car) {
                return $http.delete(prefixV1 + '/cars/' + car.id).then(extractData)
            }
        };
    });
}());