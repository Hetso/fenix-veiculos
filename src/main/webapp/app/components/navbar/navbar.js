(function () {
    'use strict';

    var components = angular.module('fv')

    components.component('fvNav', {
        templateUrl: 'app/components/navbar/navbar.html',
        controller: ['$scope', HomeController],
        bindings: {
        }
    })

    function HomeController($scope) {

        this.$onInit = function init(){
        }
    }

}())