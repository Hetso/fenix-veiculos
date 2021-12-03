(function () {
    'use strict';

    var components = angular.module('fv-setup')

    components.component('fvSetup', {
        templateUrl: 'setup/app/components/setup.html',
        controller: ['$scope', '$http', SetupController],
        bindings: {
        }
    })

    function SetupController($scope, $http) {

        this.$onInit = function init(){
        }

        $scope.user = {}

        $scope.setup = function() {
            const data = {
                user: $scope.user
            }

            $http.post('/setup/complete', data)
            .then(res => {
                localStorage.setItem("jwt", res.data.token);
                window.location = "/";
            })
            .catch(err => {
                
            })
        }
    }

}())