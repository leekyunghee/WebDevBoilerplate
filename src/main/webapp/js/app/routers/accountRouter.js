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

	return Backbone.Router.extend({

		routes : {
			"system/account(/:id)" : "account"
		},
		account : function(id) {
			baseView.render();

			require(['views/system/account/list'], function(ListView) {
				var listView = new ListView().render();
				baseView.selectMenuItem('account');
				if (id !== null) {
					require(['views/system/account/detail'], function() {
						
					});
				}
			});

		}

	});

});