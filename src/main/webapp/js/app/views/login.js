define(function(require) {

	"use strict";

	// require library
	var $ = require('jquery');
	var _ = require('underscore');
	var Backbone = require('backbone');

	// require template
	var tpl = require('text!tpl/login.html');
	var template = _.template(tpl);
	
	return Backbone.View.extend({
		render : function() {
			this.$el.html(template());
			return this;
		},
		events : {
			"click #loginBtn" : "login"
		},
		login : function(event) {
			var user = $('#userid').val(), password = $('#password').val();
			location.href = '#employeeList';
		}
	});
});