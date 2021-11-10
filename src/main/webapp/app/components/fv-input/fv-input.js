(function () {
    'use strict';

    var components = angular.module('fv')

    components.component('fvInput', {
        templateUrl: 'app/components/fv-input/fv-input.html',
        controller: ['$scope', InputController],
        bindings: {
            type: '@',
            label: '@',
            name: '@',
            model: '=',
            autofocus: '@',
            icon: '@',
            iconTooltip: '@',
            iconCallback: '<',
            enterCallback: '<'
        }
    })

    function InputController($scope) {

        this.$onInit = function init(){
            
        }
    }

}())