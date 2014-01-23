'use strict';
/* Services */

angular.module('myApp.services', ['ngResource']) //
.factory('Constituency', [ '$resource', function($resource) {
	var baseurl = 'backend/constituency/';
	return $resource(baseurl + ':id/', {
		id : '@id'
	});
}])
.factory('Counties', [ '$resource', function($resource) {
	var baseurl = 'backend/county/votes/';
	return $resource(baseurl + ':level/', {
		id : '@level'
	},
    {
        'get': {
            method: 'GET',
            transformResponse: function (data) {
            	var dat = angular.fromJson(data);
            	return dat.list ? dat.list : dat;
            },
            isArray: true
        }
    });
}])
.factory('Parties', [ '$resource', function($resource) {
	var baseurl = 'backend/party/';
	return $resource(baseurl, { },
    {
        'get': {
            method: 'GET',
            cache : true,
            transformResponse: function (data) {
            	var dat = angular.fromJson(data);
            	return dat.list ? dat.list : dat;
            },
            isArray: true
        }
    });
}]);

