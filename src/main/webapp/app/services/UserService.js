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
            },
            saveUser: function(user) {
                if(user.id) {
                    return $http.put(prefixV1 + '/' + user.id, user).then(extractData)
                }

                return $http.post(prefixV1, user).then(extractData)
            },
            findAllUsers: function(status, search) {
                return $http.get(prefixV1 + `?userStatus=${status}` + (search ? '&search='  + search : '')).then(extractData)
            },
            activeUser: function(user) {
                return $http.put(prefixV1 + `/${user.id}/active`).then(extractData)
            },
            inactiveUser: function(user) {
                return $http.put(prefixV1 + `/${user.id}/inactive`).then(extractData)
            }
        };
    });
}());