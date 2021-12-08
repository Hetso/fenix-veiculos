(function () {
    'use strict';

    var components = angular.module('fv.components')

    components.component('fvAdminUsers', {
        templateUrl: 'app/components/admin/admin-users.html',
        controller: ['$scope', '$mdDialog', 'UserService', AdminUserController],
        bindings: {
            users: '<'
        }
    })

    function AdminUserController($scope, $mdDialog, UserService) {

        this.$onInit = function init(){
            const { users } = this;

            reloadUsers(users);
        }

        $scope.userStatus = 'ACTIVE';
        $scope.editUser = function(user) {
            reloadUsers();

            $mdDialog.show({
                templateUrl: 'app/components/admin/dialog/edit-user-dialog.html',
                controller: function () {
                    const ctrl = this;

                    ctrl.editingUser = user ? angular.copy(user) : {};
                    
                    ctrl.saveUser = saveUser;

                    ctrl.genders = ['FEMALE', 'MALE', 'UNDEFINED']

                    ctrl.getGender = $scope.getGender;

                    ctrl.close = function () {
                        reloadUsers();
                        $mdDialog.hide();
                    };
                },
                controllerAs: 'ctrl'
            });
        }

        $scope.getGender = function(gender) {
            const genderTranslate = {
                'FEMALE': 'FEMININO',
                'MALE': 'MASCULINO',
                'UNDEFINED': 'INDEFINIDO',
            }

            return genderTranslate[gender] || genderTranslate['UNDEFINED'];
        }

        $scope.switch = {
            userStatus: function(value) {
                return  $scope.userStatus === 'ACTIVE' ? true : false;
            }
        }

        $scope.setUserViewStatus = function(status) {
            $scope.userStatus = status;
            reloadUsers();
        }

        $scope.changeUserStatus = function(user) {
            user.viewDisabled = user.active;

            $mdDialog.show({
                templateUrl: 'app/components/admin/dialog/active-inactive-user-dialog.html',
                controller: function () {
                    const ctrl = this;

                    ctrl.currentUser = user ? angular.copy(user) : {};

                    ctrl.changeUserStatus = function(user) {
                        if(!user.active) {
                            activeUser(user);
                        } else {
                            inactiveUser(user);
                        }
                    };

                    ctrl.close = function () {
                        $mdDialog.hide();
                    };
                },
                controllerAs: 'ctrl'
            });
        }

        function activeUser(user) {
            UserService.activeUser(user).finally(function() {
                $mdDialog.hide();
                reloadUsers();
            });
        }

        function inactiveUser(user) {
            UserService.inactiveUser(user).finally(function() {
                $mdDialog.hide();
                reloadUsers();
            });
        }

        $scope.reloadUsers = reloadUsers

        function saveUser(user) {
            UserService.saveUser(user)
            .then(res => {
            })
            .finally(function() {
                $mdDialog.hide();
                reloadUsers();
            })
        }

        function reloadUsers(users) {
            $scope.usersLoading = true;

            setTimeout(reload, 300)
            
            function reload() {
                if(users && users.length) {
                    $scope.users = users;
                    $scope.usersLoading = false;
                    $scope.$digest();
                    return;
                }

                UserService.findAllUsers($scope.userStatus, $scope.currentUserearch)
                .then(function(res) {
                    $scope.users = res;
                })
                .finally(function() {
                    $scope.usersLoading = false;
                });
            }
        }
    }

}())