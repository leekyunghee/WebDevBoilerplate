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
				contentType : 'application/json',
				data : JSON.stringify(this.toJSON()),
				success : options.success,
				error : options.error
			});
		}
	});
	
	return new SignInModel();

});