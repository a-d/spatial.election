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


angular.module('myApp.services', ['ngResource'])
.factory('Constituencies', [ '$resource', function($resource) {
	var baseurl = 'backend/constituency/detail/';
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
            isArray: true //since your list property is an array
        }
    });
}]);