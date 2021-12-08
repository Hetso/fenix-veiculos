(function() {
    'use strict';

    var components = angular.module('fv.components')

    components.component('fvLogin', {
        templateUrl: 'app/components/auth/login.html',
        controller: ['$scope', 'AuthenticationService', '$state', '$rootScope', LoginController],
    })

    function LoginController($scope, AuthenticationService, $state, $rootScope) {

        this.$onInit = function init() {}

        $scope.credentials = {
            login: null,
            password: null
        }

        $scope.login = function() {
            if (!$scope.credentials.login || !$scope.credentials.password) {
                $scope.invalidCredentials = true;
                return;
            }

            $scope.loading = true;

            AuthenticationService.login($scope.credentials)
                .then(res => {
                    $scope.invalidCredentials = false;
                    localStorage.setItem('jwt', res.token);
                    $rootScope.currentUser = res.user;
                    $state.go('home');
                })
                .catch(err => {
                    $scope.invalidCredentials = true;
                })
                .finally(() => $scope.loading = false)
        }
    }

}())