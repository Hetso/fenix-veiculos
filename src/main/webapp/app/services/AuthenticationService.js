(function () {
    'use strict';

    const services = angular.module('fv.services');

    const extractData = function (data) {
        return data.data;
    };

    const prefixV1 = '/api/v1/auth';

    services.factory('AuthenticationService', function ($http) {
        return {
            login: function(credentials){
               return $http.post(prefixV1, credentials).then(extractData)
            },
            getCurrentUser: function() {
                return $http.get(prefixV1 + '/currentUser').then(extractData)
            }
        };
    });
}());