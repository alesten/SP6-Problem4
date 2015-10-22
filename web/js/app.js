var myApp = angular.module('DemoApp', ['ngRoute']);
myApp.config(function ($routeProvider) {
    $routeProvider
            .when("/list", {
                templateUrl: "views/car/list.html",
                controller: "AllCarsController"
            })
            .when("/new", {
                templateUrl: "views/car/new.html",
                controller: "AddEditController"
            })
            .when("/new/:id", {
                templateUrl: "views/car/new.html",
                controller: "AddEditController"
            })
            .otherwise({
                redirectTo: "/list"
            });
});

myApp.factory('CarFactory', function ($http, $q) {
    this.cars = {};
    var getCars = function () {
        var defer = $q.defer();
        $http.get('api/car').success(function (data) {
            this.cars = data;
            defer.resolve(data);
        });
        return defer.promise;
    }
    var deleteCar = function (id) {
        $http.delete('api/car/' + id);
        for (var i = 0; i < cars.length; i++) {
            if (cars[i].id === id) {
                cars.splice(i, 1);
                return;
            }
        }
    }
    var addEditCar = function (newcar) {
        if (newcar.id == null) {
            $http.post('api/car', newcar);
        }
        else {
            $http.put('api/car', newcar);
            for (var i = 0; i < cars.length; i++) {
                if (cars[i].id === newcar.id) {
                    cars[i] = newcar;
                    break;
                }
            }
        }
    }
    return {
        getCars: getCars,
        deleteCar: deleteCar,
        addEditCar: addEditCar
    };
});

myApp.controller('AllCarsController', function (CarFactory, $scope, $log) {
    CarFactory.getCars().then(function (d) {
        $scope.cars = d;
    });
    $scope.title = "Cars Demo App"
    $scope.predicate = "year"
    $scope.reverse = false;

    $scope.delete = function (id) {
        CarFactory.deleteCar(id);
    };
});

myApp.controller('AddEditController', function (CarFactory, $routeParams, $window, $scope, $log) {
    $scope.car = {};
    if (angular.isDefined($routeParams.id)) {
        CarFactory.getCars().then(function (d) {
            var cars = d;
            for (var i in cars) {
                if (cars[i].id == $routeParams.id) {
                    $scope.car = angular.copy(cars[i]);
                }
            }
        });
    }
    $scope.save = function () {
        CarFactory.addEditCar($scope.car);
        $window.location.href = '#/list';
    };
});

