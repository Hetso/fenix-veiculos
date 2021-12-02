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

        this.$onInit = function init(){
            const {brands } = this;

            $scope.brands = brands;
        }

        $scope.editBrand = function(brand) {
            $mdDialog.show({
                templateUrl: 'app/components/admin/dialog/edit-brand-dialog.html',
                controller: function () {
                    const ctrl = this;
                    console.log('oi')
                    ctrl.editingBrand = brand ? angular.copy(brand) : {};

                    ctrl.saveBrand = saveBrand;

                    ctrl.close = function () {
                        $mdDialog.hide();
                    };
                },
                controllerAs: 'ctrl'
            });
        }

        $scope.removeBrand = function(brand) {
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

        function removeBrandConfirm(brand) {
            CarService.removeBrand(brand)
            .then(function() {
            })
            .finally(function() {
                $mdDialog.hide();
                reloadBrands();
            })
        }

        $scope.reloadBrands = reloadBrands

        function saveBrand(brand) {
            CarService.saveBrand(brand)
            .then(function(res) {

            })
            .finally(function() {
                $mdDialog.hide();
                reloadBrands();
            });
        }

        function reloadBrands() {
            CarService.findAllBrands($scope.currentBrandSearch).then(res => {
                $scope.brands = res;
            })
        }
    }

}())