(function () {
    'use strict';

    const services = angular.module('fv.services');

    const extractData = function (data) {
        return data.data;
    };

    const prefixV1 = '/api/v1/users';

    services.factory('UserService', function ($http) {
        return {
            createUser: function(data){
               return $http.post(prefixV1, data).then(extractData)
            },
            forgotPassword: function(data) {
                return $http.post(prefixV1 + '/forgotPassword', data).then(extractData)
            },
            recoveryPassword: function(data) {
                return $http.post(prefixV1 + '/recoveryPassword', data).then(extractData)
            }
        };
    });
}());