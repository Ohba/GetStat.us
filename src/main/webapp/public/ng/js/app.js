angular.module('getstatus', ['ngResource'])
    .factory('Cloud', function($resource) {
        return $resource('/clouds/:name');
    });

function cloudCtrl($scope, Cloud){
    $scope.clouds = Cloud.query();

    $scope.getCloud = function(name){
        $scope.cloud = Cloud.get({name : name});
    }
}