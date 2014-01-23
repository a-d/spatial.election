'use strict';
/* Services */

angular.module('myApp.services', ['ngResource']) //
.factory('Constituency', [ '$resource', function($resource) {
	var baseurl = 'backend/constituency/';
	return $resource(baseurl + ':id/', {
		id : '@id'
	}, {
		add_new_attribute : {
			method : 'POST',
			url : baseurl + ':id/attributes/:attribute_key_id/',
			headers : {
				'Content-Type' : 'text/plain'
			}
		},
		del_attribute : {
			method : 'DELETE',
			url : baseurl + ':id/attributes/:attribute_value_id/'
		}
	});
}]);


angular.module('myApp.services')
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
}]);


angular.module('myApp.services')
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

