(function () {
    'use strict';

    var components = angular.module('fv.components')

    components.component('fvHome', {
        templateUrl: 'app/components/home/home.html',
        controller: ['$scope', 'CarService', '$state', HomeController],
        bindings: {
            cars: '<',
            brands: '<'
        }
    })

    function HomeController($scope, CarService, $state) {

        this.$onInit = function init(){
            reloadCars(this.cars);
            reloadBrands(this.brands);
        }

        $scope.cars = [];
        $scope.brands = [];

        $scope.currentCarSearch = null;

        $scope.reloadCars = reloadCars;

        $scope.viewCar = function(car) {
            $state.go('car', {id: car.id})
        }

        function reloadCars(cars) {
            $scope.currentBrandView = null;

            if(cars && cars.length) {
                $scope.cars = cars;
            }

            CarService.findAllCars('ENABLED',  $scope.currentCarSearch)
            .then(res => {
                $scope.cars = res;
            })
        }

        function reloadBrands(brands) {
            if(brands && brands.length) {
                $scope.brands = brands;
            }

            CarService.findAllBrands('ENABLED')
            .then(res => {
                $scope.brands = res;
            })
        }

        $scope.searchCarsByBrand = function(brand) {
            if($scope.currentBrandView && $scope.currentBrandView.id == brand.id) {
                reloadCars();
                $scope.currentBrandView = null;
                return
            }

            CarService.findBrandById(brand.id)
            .then(res => {
                $scope.currentBrandView = brand;
                $scope.cars = res.cars
            })
        }

        $scope.getImageUrl = function(filename) {
            return filename ? CarService.getImageUrl(filename) : 'img/no-image.png'
        }
    }

}())