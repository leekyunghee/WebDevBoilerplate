define(function(require) {

	"use strict";

	// require library
	var $ = require('jquery'), Backbone = require('backbone');

	var $container;

	return Backbone.Router.extend({

		routes : {
			"" : "login",
			"login" : "login",
			"dashboard" : "dashboard",
			"expire" : "expire"
		},
		setOptions : function(options) {
			$container = options.$container;
		},
		login : function() {
			$container.empty();
			$container.removeClass();
			var LoginView = require('views/login');
			var loginView = new LoginView({
				el : $container
			}).render();
		},
		dashboard : function() {
			require([ 'views/dashboard' ], function(Dashboard) {
				var dashboard = new Dashboard({
					el : $container
				}).render();
			});
		},
		expire : function() {
		}

	});

});