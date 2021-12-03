const module = angular.module('fv-setup', ['ui.router']);

module.config(['$stateProvider', '$urlRouterProvider', '$locationProvider',  function ($stateProvider, $urlRouterProvider, $locationProvider) {

     // remover símbolos do url do ui-router junto com a tag <base href> no index.html
     $locationProvider.html5Mode(true);

     var path = window.location.pathname;
     // remove barra do final pra não dar otherwise no urlRouterProvider
     if (path.length > 1 && path[path.length - 1] === '/' || path.indexOf('/?') > -1) {
         window.location = path.substr(0, path.length - 1);
     }

    $stateProvider
        .state('home', {
            url: '/setup',
            template: '<fv-setup></fv-setup>',
        })

    $urlRouterProvider.otherwise('/setup');
}])