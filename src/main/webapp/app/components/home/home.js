(function () {
    'use strict';

    var components = angular.module('fv')

    components.component('fvHome', {
        templateUrl: 'app/components/home/home.html',
        controller: ['$scope', '$mdDialog', 'CarService', HomeController],
        bindings: {
            cars: '<'
        }
    })

    function HomeController($scope, $mdDialog, CarService) {

        this.$onInit = function init(){
            const { cars } = this;

            $scope.cars = cars;
        }

        $scope.editCar = function(car) {
            $mdDialog.show({
                templateUrl: 'app/components/car/edit-car-dialog.html',
                controller: function () {
                    const ctrl = this;
                    
                    ctrl.editingCar = car ? angular.copy(car) : {};

                    ctrl.saveCar = saveCar;

                    ctrl.close = function () {
                        $mdDialog.hide();
                    };
                },
                controllerAs: 'editCarCtrl'
            });
        }

        $scope.removeCar = function(car) {
            CarService.removeCar(car)
            .then(function() {
                reloadCars();
            })
        }

        function saveCar(car) {
            CarService.saveCar(car)
            .then(function(res) {

            })
            .finally(function() {
                $mdDialog.hide();
                reloadCars();
            });
        }

        function reloadCars() {
            CarService.findAllCars()
            .then(function(res) {
                $scope.cars = res
            });

        }
    }

}())