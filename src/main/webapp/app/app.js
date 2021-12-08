angular.module('fv.components', ['fv.services']);
angular.module('fv.directives', ['fv.services']);
angular.module('fv.services', []);
angular.module('fv.filters', []);

const module = angular.module('fv', ['fv.services', 'fv.components', 'fv.filters', 'fv.directives', 'ui.router',
    'ngSanitize', 'ngMessages', 'pascalprecht.translate', 'ngMaterial'])

// ---- CONFIGURAÇÕES ----
module.config(['$stateProvider', '$translateProvider', '$urlRouterProvider', '$locationProvider', '$mdIconProvider', 
    function ($stateProvider, $translateProvider, $urlRouterProvider, $locationProvider, $mdIconProvider) {

    // remover símbolos do url do ui-router junto com a tag <base href> no index.html
    $locationProvider.html5Mode(true);

    var path = window.location.pathname;
    // remove barra do final pra não dar otherwise no urlRouterProvider
    if (path.length > 1 && path[path.length - 1] === '/' || path.indexOf('/?') > -1) {
        window.location = path.substr(0, path.length - 1);
    }

    // Arquivo de tradução utilizado
    $translateProvider.useStaticFilesLoader({
        prefix: 'locales/',
        suffix: '.json'
    });
    $translateProvider.preferredLanguage('pt_BR');


    const brandsResolver = {
        brands: function(CarService) {
            return CarService.findAllBrands('ENABLED');
        }
    }

    // RESOLVERS
    const carsResolver = {
        cars: function(CarService) {
            return CarService.findAllCars('ENABLED');
        },
        brands: brandsResolver.brands
    }

    // ROTAS ui-router

    $stateProvider
        .state('home', {   
            url: '/',
            template: '<fv-home cars="rslvr.cars" brands="rslvr.brands" class="main-content"></fv-home>',
            resolve: carsResolver,
            controller: function(cars, brands) {
                this.cars = cars;
                this.brands = brands;
            },
            controllerAs: 'rslvr'
        })
        .state('car', {
            url: '/car/:id',
            template: '<fv-car-view class="main-content"></fv-car-view>'
        })
        // auth
        .state('auth', {
            abstract: true,
            url: '/auth',
            templateUrl: 'app/components/auth/auth.html'
        })
        .state('auth.login', {   
            url: '/login',
            template: '<fv-login></fv-login>',
        })
        .state('auth.recoveryPassword', {   
            url: '/recoveryPassword/:token',
            template: '<fv-recovery-password></fv-recovery-password>',
        })
        .state('auth.forgotPassword', {   
            url: '/forgotPassword',
            template: '<fv-forgot-password></fv-forgot-password>',
        })
        // admin
        .state('admin', {   
            url: '/admin',
            template: '<fv-admin></fv-admin>',
            abstract: true
        })
        .state('admin.home', {
            url: '',
            template: '<fv-admin-cars cars="rslvr.cars" brands="rslvr.brands"></fv-admin-cars>',
            resolve: carsResolver,
            controller: function(cars, brands) {
                this.cars = cars;
                this.brands = brands;
            },
            controllerAs: 'rslvr'
        })
        .state('admin.brands', {
            url: '/brands',
            template: '<fv-admin-brands brands="rslvr.brands"></fv-admin-brands>',
            resolve: brandsResolver,
            controller: function(brands) {
                this.brands = brands;
            },
            controllerAs: 'rslvr'
        })
        .state('admin.users', {
            url: '/users',
            template: '<fv-admin-users></fv-admin-users>'
        })

    $urlRouterProvider.otherwise('/');

    $mdIconProvider
            .icon('add', 'svg/ic_add_white_48px.svg')
            .icon('menu', 'svg/ic_menu_white_48px.svg')
            .icon('settings', 'svg/ic_settings_white_48px.svg')
            .icon('person', 'svg/ic_person_white_48px.svg')
            .icon('person_black', 'svg/ic_person_black_48px.svg')
            .icon('group_add_black', 'svg/ic_group_add_black_48px.svg')
            .icon('person_add_black', 'svg/ic_person_add_black_48px.svg')
            .icon('circle', 'svg/ic_circle_black_48px.svg')
            .icon('calendar', 'svg/calendar.svg')
            .icon('folder-move', 'svg/folder-move.svg')
            .icon('milestone', 'svg/ic_location_on_black_48px.svg')
            .icon('label', 'svg/ic_label_black_48px.svg')
            .icon('task', 'svg/ic_assignment_black_48px.svg')
            .icon('edit', 'svg/ic_mode_edit_black_48px.svg')
            .icon('archive', 'svg/ic_archive_black_48px.svg')
            .icon('unarchive', 'svg/ic_unarchive_black_48px.svg')
            .icon('close', 'svg/ic_close_black_48px.svg')
            .icon('error', 'svg/ic_error_outline_black_48px.svg')
            .icon('ok', 'svg/ic_check_black_48px.svg')
            .icon('expand_less', 'svg/ic_expand_less_black_48px.svg')
            .icon('expand_more', 'svg/ic_expand_more_black_48px.svg')
            .icon('info', 'svg/ic_info_black_48px.svg')
            .icon('help', 'svg/ic_help_black_48px.svg')
            .icon('search', 'svg/ic_search_black_48px.svg')
            .icon('delete', 'svg/ic_delete_black_48px.svg')
            .icon('drag', 'svg/drag.svg')
            .icon('comment', 'svg/ic_comment_black_48px.svg')
            .icon('file', 'svg/ic_insert_drive_file_black_48px.svg')
            .icon('list', 'svg/ic_list_black_48px.svg')
            .icon('clock', 'svg/ic_access_time_black_48px.svg')
            .icon('notifications', 'svg/ic_notifications_black_48px.svg')
            .icon('notifications-off', 'svg/ic_notifications_off_black_48px.svg')
            .icon('take', 'svg/ic_assignment_ind_black_48px.svg')
            .icon('surrender', 'svg/ic_assignment_returned_black_48px.svg')
            .icon('upload', 'svg/ic_cloud_upload_black_48px.svg')
            .icon('replay', 'svg/ic_replay_black_48px.svg')
            .icon('align_top', 'svg/ic_vertical_align_top_black_48px.svg')
            .icon('align_bottom', 'svg/ic_vertical_align_bottom_black_48px.svg')
            .icon('milestone_open', 'svg/milestone-open.svg')
            .icon('milestone_closed', 'svg/milestone-closed.svg')
            .icon('location_backlog', 'svg/ic_developer_board_black_48px.svg')
            .icon('location_archive', 'svg/archive.svg')
            .icon('location_trash', 'svg/trash.svg')
            .icon('dashboard', 'svg/ic_dashboard_black_48px.svg')
            .icon('dashboard_white', 'svg/ic_dashboard_white_48px.svg')
            .icon('menu_ellipsis', 'svg/ic_more_vert_black_48px.svg')
            .icon('menu_ellipsis_white', 'svg/ic_more_vert_white_48px.svg')
            .icon('add_column', 'svg/add_column.svg')
            .icon('select_all', 'svg/ic_select_all_black_48px.svg')
            .icon('add-to-list', 'svg/ic_playlist_add_black_48px.svg')
            .icon('chevron-left', 'svg/ic_chevron_left_black_48px.svg')
            .icon('chevron-right', 'svg/ic_chevron_right_black_48px.svg')
            .icon('file-excel', 'svg/file-excel.svg')
            .icon('logout', 'svg/logout.svg')
            .icon('repeat', 'svg/repeat.svg')
            .icon('repeat-off', 'svg/repeat-off.svg')
            .icon('security-key', 'svg/ic_vpn_key_black_48px.svg')
            .icon('sync', 'svg/ic_sync_black_48px.svg')
            .icon('sync_disabled', 'svg/ic_sync_disabled_black_48px.svg')
            .icon('sort-ascending', 'svg/sort-ascending.svg')
            .icon('sort-descending', 'svg/sort-descending.svg');
}]);

module.run(function($rootScope, AuthenticationService) {
    AuthenticationService.getCurrentUser()
    .then(function(data) {
        $rootScope.currentUser = data;
    });
});

module.factory('fvHttpInterceptor', function ($q, $window) {
    //
    return {
        'request': function (config) {
            if(!$window.jwtToken && localStorage.getItem('jwt')) {
                $window.jwtToken = localStorage.getItem('jwt');
            }

            if (angular.isDefined($window.jwtToken) && $window.jwtToken !== null) {
                config.headers['Authorization'] = 'Bearer ' + $window.jwtToken;
            } 

            return config;
        },
        'response': function (response) {

            return response;
        },
        'responseError': function (rejection) {
            // if the session has been lost, trigger a reload
            if (rejection.status === 401) {
                $window.location = '/login';
            }

            return $q.reject(rejection);
        }
    };
});

module.config(function ($httpProvider) {
    $httpProvider.interceptors.push('fvHttpInterceptor');
    $httpProvider.useApplyAsync(true);
});