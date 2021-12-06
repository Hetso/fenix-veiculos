(function () {
    'use strict';

    var components = angular.module('fv')

    components.component('fvHome', {
        templateUrl: 'app/components/home/home.html',
        controller: ['$scope', HomeController],
        bindings: {
        }
    })

    function HomeController($scope) {

        this.$onInit = function init(){
        }
    }

}())