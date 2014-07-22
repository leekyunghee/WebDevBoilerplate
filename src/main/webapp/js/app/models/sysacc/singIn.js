define(function(require) {

	"use strict";

	var $ = require('jquery'), Backbone = require('backbone');

	var SignInModel = Backbone.Model.extend({
		defaults : {
			"userId" : "",
			"password" : "",
			"successSignIn" : ""
		},
		url : "signin",
		obtainCertification : function(options) {
			this.fetch({
				method : "POST",
//				async : false,
				contentType : 'application/json',
				data : JSON.stringify(this.toJSON()),
				success : options.success,
				error : options.error
//				success : function(data) {
//					console.log(data);
//				},
//				error : function(model, response) {
//					console.log('fetch error');
//					console.log(model);
//					console.log(response);
//				}
			});
		}
	});
	
	return new SignInModel();

});