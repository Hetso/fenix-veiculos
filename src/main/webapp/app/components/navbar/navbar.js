(function () {
    'use strict';

    var components = angular.module('fv.components')

    components.component('fvNav', {
        templateUrl: 'app/components/navbar/navbar.html',
        controller: ['$scope', '$state', 'AuthenticationService', HomeController],
        bindings: {
        }
    })

    function HomeController($scope, $state, AuthenticationService) {

        this.$onInit = function init(){
        }

        $scope.isState = function(name, exact) {
           return !exact ? $state.includes(name) : $state.is(name);
        }

        $scope.showTabs = function() {
            return !$state.includes('auth')
        }

        $scope.logout = function() {
            AuthenticationService.logout()
            .then(res => {
                window.location = "/";
            })
        }
    }

}())