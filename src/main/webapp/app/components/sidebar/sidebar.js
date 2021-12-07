(function () {
    'use strict';

    var components = angular.module('fv.components')

    components.component('fvSidebar', {
        templateUrl: 'app/components/sidebar/sidebar.html',
        controller: ['$scope', SidebarController],
        bindings: {
        }
    })

    function SidebarController($scope) {

        this.$onInit = function init(){
        }
    }

}())