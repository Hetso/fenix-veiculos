(function () {
    'use strict';

    var components = angular.module('fv')

    components.component('fvImageSelector', {
        templateUrl: 'app/components/image-selector/image-selector.html',
        controller: ['$scope', ImageSelectorController],
        bindings: {
            model: '<',
            multiple: '@',
            label: '@',
            updateCallback: '<',
            deleteCallback: '<',
            currentImages: '<',
            getImageUrl: '<'
        }
    })

    function ImageSelectorController($scope) {

        this.$onInit = function init() {
            $scope.multiple = typeof this.multiple === 'string' && this.multiple.toLowerCase() !== 'false';
            $scope.label = this.label;
            $scope.updateCallback = this.updateCallback;
            $scope.currentImages = this.currentImages;
            $scope.getImageUrl = this.getImageUrl;
            $scope.deleteCallback = this.deleteCallback;

            initSingleImage();
            initMultipleImages();
        }

        $scope.defaultImage = '/img/no-image.png';

        // single image

        $scope.single = {
            currentImage: $scope.defaultImage
        }

        $scope.singleFileChanged = function (event) {
            const file = event.files[0];
            const reader = new FileReader();
            reader.onloadend = function () {
                $scope.single.currentImage = reader.result;
                $scope.$digest();
            }

            if (file) {
                $scope.updateCallback(file);
                $scope.$digest();
                reader.readAsDataURL(file);
            } else {
            }
        }

        function initSingleImage() {
            if (!$scope.multiple && $scope.currentImages && $scope.getImageUrl) {
                $scope.single.currentImage = $scope.getImageUrl($scope.currentImages);
            }
        }

        // multiples images

        $scope.multipleImages = {
            images: []
        }

        $scope.multipleFileChanged = function (event) {
            const files = event.files;
            const readers = [];

            if (files && files.length) {
                for (var key in files) {
                    if(typeof files[key] === 'object') {
                        readers.push(new Promise((resolve, reject) => {
                            const reader = new FileReader();
                            reader.onloadend = function () {
                                $scope.multipleImages.images.push({ url: reader.result, isNew: true })
                                $scope.$digest();
    
                                resolve(files[key]);
                            }
    
                            reader.onerror = function() {
                                reject('Error on reading file')
                            }
                            reader.readAsDataURL(files[key]);
                        }))
                    }
                }
                
                Promise.all(readers).then(values => {
                    $scope.updateCallback(files);
                    $scope.$digest();
                })
            }
        }

        $scope.removeImage = function (image, index) {
            $scope.multipleImages.images.splice(index, 1);

            if (!image.isNew && image.filename) {
                $scope.deleteCallback(image.filename);
            }
        }

        function initMultipleImages() {
            if ($scope.multiple && $scope.currentImages && $scope.getImageUrl) {
                $scope.currentImages.forEach(img => {
                    $scope.multipleImages.images.push({ url: $scope.getImageUrl(img.imagePath), filename: img.imagePath })
                })
            }
        }

    }

}())