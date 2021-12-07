(function () {
    'use strict';

    var components = angular.module('fv.components')

    components.component('fvAdmin', {
        templateUrl: 'app/components/admin/admin.html',
        controller: ['$scope', '$state', AdminController],
        bindings: {
        }
    })

    function AdminController($scope, $state) {

        this.$onInit = function init(){
        }

        $scope.isState = function(name) {
            return $state.is(name);
        }
    }

}())