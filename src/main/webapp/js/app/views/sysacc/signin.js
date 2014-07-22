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
//			this.listenTo(this.model, 'change', this.success)
			// avoid 'change' event, because model.set method trigger 'change' event.
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

			signInModel.obtainCertification({
				success : this.success
			});
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