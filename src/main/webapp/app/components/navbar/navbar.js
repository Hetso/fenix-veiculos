(function () {
    'use strict';

    var components = angular.module('fv')

    components.component('fvNav', {
        templateUrl: 'app/components/navbar/navbar.html',
        controller: ['$scope', '$state', 'AuthenticationService', HomeController],
        bindings: {
        }
    })

    function HomeController($scope, $state, AuthenticationService) {

        this.$onInit = function init(){
        }

        $scope.isState = function(name) {
           return $state.includes(name);
        }

        $scope.logout = function() {
            AuthenticationService.logout()
            .then(res => {
                window.location = "/";
            })
        }
    }

}())