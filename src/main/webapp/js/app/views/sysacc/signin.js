define(function(require) {

	"use strict";

	// require library
	var $ = require('jquery');
	var _ = require('underscore');
	var Backbone = require('backbone');

	// require model
	var signInModel = require('models/sysacc/singIn');

	// require template
	var tpl = require('text!tpl/login.html');
	var template = _.template(tpl);

	// require i18n
	var locale = require('i18n!nls/str');

	return Backbone.View.extend({
		model : signInModel,
		initialize : function() {
			this.listenTo(this.model, 'change', this.success)
		},
		render : function() {
			this.$el.html(template());
			$('#userid').prop('placeholder', locale.id);
			$('#password').prop('placeholder', locale.password);
			$('#loginBtn').html(locale.sign_in);
			return this;
		},
		events : {
			"click #loginBtn" : "login"
		},
		login : function(event) {
			signInModel.set({
				userId : $('#userid').val(),
				password : $('#password').val()
			});
			 signInModel.obtainCertification();

//			signInModel.obtainCertification({
//				success : function(data) {
//					if (data.get('successSignIn') == 'Y') {
//						location.href = '#employeeList';
//					} else {
//						alert('can not sign in');
//					}
//				},
//				error : function(model, response) {
//					console.log('fetch error');
//					console.log(model);
//					console.log(response);
//				}
//			});
		},
		success : function() {
			console.log('function success in singin.js');
			console.log(signInModel.toJSON());
			if (signInModel.get('successSignIn') == '') {
				console.log('successSignIn field is empty.');
				return;
			} else if (signInModel.get('successSignIn') == 'Y'){
				location.hash = '#employeeList';
			} else {
				alert('can not sign in');
			}
		}
	});
});