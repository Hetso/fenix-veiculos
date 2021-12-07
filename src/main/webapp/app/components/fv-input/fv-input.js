(function() {
    'use strict';

    var components = angular.module('fv.components')

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
            enterCallback: '<',
            required: '@'
        }
    })

    function InputController($scope) {

        this.$onInit = function init() {
            $scope.required = this.required !== undefined && this.required !== 'false'
        }
    }

}())