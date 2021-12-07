(function () {
    'use strict';

    var components = angular.module('fv.components')

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

            reloadBrands(brands);
        }

        $scope.brandStatus = 'ENABLED'

        $scope.switch = {
            brandStatus: function(value) {
                return  $scope.brandStatus === 'ENABLED' ? true : false;
            }
        }

        $scope.editBrand = function (brand) {
            $mdDialog.show({
                templateUrl: 'app/components/admin/dialog/edit-brand-dialog.html',
                controller: function () {
                    const ctrl = this;
                    ctrl.editingBrand = brand ? angular.copy(brand) : {};

                    ctrl.saveBrand = saveBrand;

                    ctrl.getImageUrl = $scope.getImageUrl;

                    ctrl.logoImageUpdate = function(file) {
                        ctrl.editingBrand.logoFile = file;
                    }

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

        $scope.getImageUrl = function(filename) {
            return filename ? CarService.getImageUrl(filename) : 'img/no-image.png'
        };

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
            const isUpdatingLogo = !!brand.logoFile

            CarService.saveBrand(brand)
                .then(function (res) {
                    if(isUpdatingLogo) {
                        CarService.uploadBrandLogo({logo: brand.logoFile, brandId: res.id}).finally(function() {
                            reloadBrands();
                        })
                    }
                })
                .finally(function () {
                    $mdDialog.hide();

                    if(!isUpdatingLogo) {
                        reloadBrands();
                    }
                });
        }

        function reloadBrands(brands) {
            $scope.brandsLoading = true;

            setTimeout(reload, 300);
            
            function reload() {
                if(brands && brands.length) {
                    $scope.brands = brands;
                    $scope.brandsLoading = false;
                    $scope.$digest();
                    return;
                }

                CarService.findAllBrands($scope.brandStatus, $scope.currentBrandSearch)
                .then(res => {
                    $scope.brands = res;
                })
                .finally(function() {
                    $scope.brandsLoading = false;
                })
            }
        }
    }

}())