'use strict';
/* Services */

angular.module('myApp.services', ['ngResource']) //
.factory('Constituency', [ '$resource', function($resource) {
	var baseurl = 'rest/constituency/';
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