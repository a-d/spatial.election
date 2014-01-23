'use strict';


//Declare app level module which depends on filters, and services
var app = angular.module('myApp', [
                                   'ngResource',
                                   'myApp.services',
                                   'myApp.directives',
                                   'myApp.controllers'
                                   ]);
angular.module('d3', []);
angular.module('Counties', []);
angular.module('Parties', []);
angular.module('myApp.directives', ['d3', 'Counties', 'Parties']);
