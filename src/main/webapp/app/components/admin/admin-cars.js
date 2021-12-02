(function () {
    'use strict';

    var components = angular.module('fv')

    components.component('fvAdminCars', {
        templateUrl: 'app/components/admin/admin-cars.html',
        controller: ['$scope', AdminCarController],
        controller: ['$scope', '$mdDialog', 'CarService', AdminCarController],
        bindings: {
            cars: '<',
            brands: '<'
        }
    })

    function AdminCarController($scope, $mdDialog, CarService) {

        this.$onInit = function init(){
            const { cars, brands } = this;

            $scope.cars = cars;
            $scope.brands = brands;
        }

        $scope.editCar = function(car) {
            reloadBrands();

            $mdDialog.show({
                templateUrl: 'app/components/admin/dialog/edit-car-dialog.html',
                controller: function () {
                    const ctrl = this;
                    
                    ctrl.editingCar = car ? angular.copy(car) : {};
                    ctrl.brands = $scope.brands;

                    ctrl.saveCar = saveCar;

                    ctrl.close = function () {
                        $mdDialog.hide();
                    };
                },
                controllerAs: 'editCarCtrl'
            });
        }

        $scope.removeCar = function(car) {
            $mdDialog.show({
                templateUrl: 'app/components/admin/dialog/remove-car-dialog.html',
                controller: function () {
                    const ctrl = this;
                    
                    ctrl.currentCar = car ? angular.copy(car) : {};

                    ctrl.removeCar = removeCarConfirm;

                    ctrl.close = function () {
                        $mdDialog.hide();
                    };
                },
                controllerAs: 'removeCarCtrl'
            });
        }

        function removeCarConfirm(car) {
            CarService.removeCar(car)
            .then(function() {
            })
            .finally(function() {
                $mdDialog.hide();
                reloadCars();
            })
        }

        $scope.reloadCars = reloadCars

        function saveCar(car) {
            car.brandId = car.brand.id

            CarService.saveCar(car)
            .then(function(res) {

            })
            .finally(function() {
                $mdDialog.hide();
                reloadCars();
            });
        }

        function reloadCars() {
            CarService.findAllCars($scope.currentCarSearch)
            .then(function(res) {
                $scope.cars = res
            });

        }

        function reloadBrands() {
            CarService.findAllBrands().then(res => {
                $scope.brands = res;
            })
        }
    }

}())