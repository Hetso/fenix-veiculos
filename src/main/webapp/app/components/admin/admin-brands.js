(function () {
    'use strict';

    var components = angular.module('fv')

    components.component('fvAdminBrands', {
        templateUrl: 'app/components/admin/admin-brands.html',
        controller: ['$scope', AdminBrandController],
        controller: ['$scope', '$mdDialog', 'CarService', AdminBrandController],
        bindings: {
            brands: '<'
        }
    })

    function AdminBrandController($scope, $mdDialog, CarService) {

        this.$onInit = function init() {
            const { brands } = this;

            $scope.brands = brands;
        }

        $scope.brandStatus = 'ENABLED'

        $scope.editBrand = function (brand) {
            $mdDialog.show({
                templateUrl: 'app/components/admin/dialog/edit-brand-dialog.html',
                controller: function () {
                    const ctrl = this;
                    ctrl.editingBrand = brand ? angular.copy(brand) : {};

                    ctrl.saveBrand = saveBrand;

                    ctrl.close = function () {
                        $mdDialog.hide();
                    };
                },
                controllerAs: 'ctrl'
            });
        }

        $scope.removeBrand = function (brand) {

            $mdDialog.show({
                templateUrl: 'app/components/admin/dialog/remove-brand-dialog.html',
                controller: function () {
                    const ctrl = this;

                    ctrl.currentBrand = brand ? angular.copy(brand) : {};

                    ctrl.removeBrand = removeBrandConfirm;

                    ctrl.close = function () {
                        $mdDialog.hide();
                    };
                },
                controllerAs: 'ctrl'
            });
        }

        $scope.setBrandViewStatus = function (status) {
            $scope.brandStatus = status;
            reloadBrands();
        }

        $scope.changeBrandStatus = function(brand) {
            brand.viewDisabled = !brand.disabled;

            $mdDialog.show({
                templateUrl: 'app/components/admin/dialog/disable-enable-brand-dialog.html',
                controller: function () {
                    const ctrl = this;

                    ctrl.currentBrand = brand ? angular.copy(brand) : {};

                    ctrl.changeBrandStatus = function(brand) {
                        if(brand.disabled) {
                            enableBrand(brand);
                        } else {
                            disableBrand(brand);
                        }
                    };

                    ctrl.close = function () {
                        $mdDialog.hide();
                    };
                },
                controllerAs: 'ctrl'
            });
        }

        function enableBrand(brand) {
            CarService.enableBrand(brand).then(res => {
                reloadBrands();
                $mdDialog.hide();
            });
        }

        function disableBrand(brand) {
            CarService.disableBrand(brand).then(res => {
                reloadBrands();
                $mdDialog.hide();
            });
        }

        function removeBrandConfirm(brand) {
            CarService.removeBrand(brand)
                .then(function () {
                })
                .finally(function () {
                    $mdDialog.hide();
                    reloadBrands();
                })
        }

        $scope.reloadBrands = reloadBrands

        function saveBrand(brand) {
            CarService.saveBrand(brand)
                .then(function (res) {

                })
                .finally(function () {
                    $mdDialog.hide();
                    reloadBrands();
                });
        }

        function reloadBrands() {
            CarService.findAllBrands($scope.brandStatus, $scope.currentBrandSearch).then(res => {
                $scope.brands = res;
            })
        }
    }

}())