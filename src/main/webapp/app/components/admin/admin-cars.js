(function () {
    'use strict';

    var components = angular.module('fv.components')

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

            reloadBrands(brands);
            reloadCars(cars);
        }

        $scope.brandStatus = 'ENABLED';
        $scope.editCar = function(car) {
            reloadBrands();

            $mdDialog.show({
                templateUrl: 'app/components/admin/dialog/edit-car-dialog.html',
                controller: function () {
                    const ctrl = this;

                    ctrl.getImageUrl = $scope.getImageUrl;
                    
                    ctrl.editingCar = car ? angular.copy(car) : {};
                    ctrl.brands = $scope.brands;
                    
                    ctrl.editingCar.coverImageFile = null;
                    ctrl.editingCar.imagesFiles = [];

                    ctrl.coverImageUpdate = function(file) {
                        ctrl.editingCar.coverImageFile = file;
                    }

                    ctrl.imagesUpdate = function(files) {
                        for(var key in files) {
                            if(typeof files[key] === 'object') {
                                ctrl.editingCar.imagesFiles.push(files[key]);
                            }
                        }
                    }

                    ctrl.deleteImage = function(filename) {
                        return CarService.removeCarImage(ctrl.editingCar.id, filename);
                    }

                    ctrl.saveCar = saveCar;

                    ctrl.close = function () {
                        reloadCars();
                        $mdDialog.hide();
                    };
                },
                controllerAs: 'editCarCtrl'
            });
        }

        $scope.getImageUrl = function(filename) {
            return filename ? CarService.getImageUrl(filename) : 'img/no-image.png'
        };

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

        $scope.setBrandViewStatus = function(status) {
            $scope.brandStatus = status;
            reloadCars();
        }

        $scope.reloadCars = reloadCars

        function saveCar(car) {
            car.brandId = car.brand.id

            var isUploadImage = car.coverImageFile || (car.imagesFiles && car.imagesFiles.length);

            CarService.saveCar(car)
            .then(function(res) {
                if(isUploadImage) {
                    CarService.uploadCarImages({coverImage: car.coverImageFile, images: car.imagesFiles, carId: res.id})
                    .finally(function() {
                        reloadCars();
                    });
                }
            })
            .finally(function() {
                $mdDialog.hide();
                if(!isUploadImage) {
                    reloadCars();
                }
            });
        }

        function reloadCars(cars) {
            $scope.carsLoading = true;

            setTimeout(reload, 300)
            
            function reload() {
                if(cars && cars.length) {
                    $scope.cars = cars;
                    $scope.carsLoading = false;
                    $scope.$digest();
                    return;
                }

                CarService.findAllCars($scope.brandStatus, $scope.currentCarSearch)
                .then(function(res) {
                    $scope.cars = res;
                })
                .finally(function() {
                    $scope.carsLoading = false;
                });
            }
        }

        function reloadBrands(brands) {
            if(brands && brands.length) {
                $scope.brands = brands;
                return;
            }

            CarService.findAllBrands('ENABLED').then(res => {
                $scope.brands = res;
            })
        }
    }

}())