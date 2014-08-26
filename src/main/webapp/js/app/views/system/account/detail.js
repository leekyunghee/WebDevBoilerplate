define(function(require) {

	"use strict";

	// require library
	var $ = require('jquery'),
		_ = require('underscore'),
		Backbone = require('backbone'),
		tpl = require('text!tpl/system/account/accountDetail.html');

	// require model
	var AccountModel = require('models/system/account'),
		accountModel = new AccountModel();

	var template = _.template(tpl);

	return Backbone.View.extend({

		initialize : function() {
		},

		render : function(options) {
			var self = this;
			this.$el.empty();

			accountModel.set({
				username : options.username
			}, {
				silent : true,
				validate : false
			});
			accountModel.fetch({
				method : "POST",
				contentType : 'application/json',
				data : JSON.stringify(accountModel.toJSON()),
				success : function(model, response, options) {
					self.$el.html(template(model.attributes));
				},
				error : function(model, response) {
					console.log('fetch error');
					console.log(model);
					console.log(response);
				}
			});
			
			options.listView.selectAccountItem(options.username);

			return this;
		}
	});

});
