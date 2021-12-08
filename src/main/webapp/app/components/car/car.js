(function () {
    'use strict';

    var components = angular.module('fv.components')

    components.component('fvCarView', {
        templateUrl: 'app/components/car/car.html',
        controller: ['$scope', 'CarService', '$stateParams', CarController]
    })

    function CarController($scope, CarService, $stateParams) {

        this.$onInit = function init(){
            loadCar($stateParams.id);
        }

        $scope.car = {};
        $scope.currentImageView = null;

        $scope.getImageUrl = function(filename) {
            return filename ? CarService.getImageUrl(filename) : 'img/no-image.png'
        };

        $scope.changeCurrentImageView = function(filename) {
            $scope.currentImageView = filename;
        }

        function loadCar(id) {
            CarService.findCarById(id)
            .then(res => {
                $scope.car = res;
                $scope.currentImageView = res.coverImage;
            })
        }
        
    }

}())