'use strict';


//Declare app level module which depends on filters, and services
var app = angular.module('myApp', [
                                   'ngResource',
                                   'myApp.filters',
                                   'myApp.services',
                                   'myApp.directives',
                                   'myApp.controllers'
                                   ]);
angular.module('d3', []);
angular.module('Counties', []);
angular.module('myApp.directives', ['d3', 'Counties']);

function mainCtrl($scope, Constituency) {

	$scope.testdata = "string";
	$scope.status = "pending";
	$scope.data2 = Constituency.get({'id' : 100});
	$scope.data2.$promise.then(function (result) {
		$scope.data2 = result;
	});

	$scope.click = function() {
		$scope.data = Constituency.get({'id' : 67}, function() {
			$scope.status = "success";
		}, function() {
			$scope.status = "failure";
		});
	};
};
function demoCtrl1($scope) {
	$scope.title = "DemoCtrl";

	$scope.d3Data = [
	                 {name: "Greg", score:98},
	                 {name: "Ari", score:96},
	                 {name: "Loser", score: 48}
	                 ];
	$scope.d3OnClick = function(item){
		alert(item.name);
	};
};
function demoCtrl2($scope) {
	$scope.title = "DemoCtrl2";
	$scope.d3Data = [
	                 {title: "Greg", score:12},
	                 {title: "Ari", score:43},
	                 {title: "Loser", score: 87}
	                 ];
};