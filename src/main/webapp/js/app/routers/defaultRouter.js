define(function(require) {

	"use strict";

	// require library
	var $ = require('jquery'), Backbone = require('backbone');

	// require view
	var BaseView = require('views/base')

	// require selector
	var $body = $('body'), baseView = new BaseView({
		el : $body
	}), $main = $('#main', baseView.el);

	// Close the search dropdown on click anywhere in the UI
	// $body.click(function() {
	// $('.dropdown').removeClass("open");
	// });

	return Backbone.Router.extend({

		routes : {
			"" : "login",
			"login" : "login",
			"dashboard" : "dashboard",
			"etc" : "etc"
		},
		login : function() {
			var LoginView = require('views/login');
			var loginView = new LoginView({
				el : $body
			}).render();
		},
		dashboard : function() {
			baseView.render();
			baseView.selectMenuItem();
		},
		etc : function() {
			baseView.render();
			baseView.selectMenuItem('etc');
		}

	});

});