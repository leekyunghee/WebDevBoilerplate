define(function(require) {

	"use strict";

	var $ = require('jquery'), Backbone = require('backbone');

	var SignInModel = Backbone.Model.extend({
		defaults : {
			"userId" : "",
			"password" : "",
			"successLogin" : ""
		},
		url : "login",
		obtainCertification : function(options) {
			this.fetch({
				method : "POST",
				contentType : 'application/json',
				data : JSON.stringify(this.toJSON()),
				success : options.success,
				error : function(model, response) {
					console.log('fetch error');
					console.log(model);
					console.log(response);
				}
			});
		}
	});

	return new SignInModel();

});