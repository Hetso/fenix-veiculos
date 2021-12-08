(function() {
    'use strict';

    var components = angular.module('fv.components')

    components.component('fvRecoveryPassword', {
        templateUrl: 'app/components/auth/recovery-password.html',
        controller: ['$scope', 'UserService', '$stateParams', RecoveryController],
    })

    function RecoveryController($scope, UserService, $stateParams) {

        this.$onInit = function init() {
            $scope.credentials.token = $stateParams.token
        }

        $scope.credentials = {
            password: null
        }

        $scope.handleRecovery = function() {
            $scope.invalidPassword = false;
            $scope.invalidCredentials = false;

            if($scope.credentials.password !== $scope.credentials.confirmPassword) {
                $scope.invalidPassword = true;
                return;
            }

            if (!$scope.credentials.password || !$scope.credentials.token) {
                $scope.invalidCredentials = true;
                return;
            }

            $scope.loading = true;

            UserService.recoveryPassword($scope.credentials)
                .then(res => {
                    $scope.invalidCredentials = false;
                    $scope.invalidPassword = false;
                    window.location = '/auth/login'
                })
                .catch(err => {
                    $scope.invalidCredentials = true;
                })
                .finally(() => $scope.loading = false)
        }
    }

}())