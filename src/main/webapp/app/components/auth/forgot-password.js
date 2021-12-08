(function() {
    'use strict';

    var components = angular.module('fv.components')

    components.component('fvForgotPassword', {
        templateUrl: 'app/components/auth/forgot-password.html',
        controller: ['$scope', 'UserService', ForgotController],
    })

    function ForgotController($scope, UserService) {

        this.$onInit = function init() {}

        $scope.credentials = {
            login: null
        }

        $scope.handleForgot = function() {
            if (!$scope.credentials.login) {
                $scope.invalidCredentials = true;
                return;
            }

            $scope.loading = true;

            UserService.forgotPassword($scope.credentials)
                .then(res => {
                    $scope.invalidCredentials = false;
                    $scope.sended = true;
                })
                .catch(err => {
                    $scope.invalidCredentials = true;
                })
                .finally(() => $scope.loading = false)
        }
    }

}())