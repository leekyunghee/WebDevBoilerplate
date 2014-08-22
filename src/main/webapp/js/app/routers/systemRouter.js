define(function(require) {

	"use strict";

	// require library
	var $ = require('jquery'), Backbone = require('backbone');

	// require View
	var LayoutView = require('views/layout'), layoutView, frameView;

	var $container, $list, $main;

	return Backbone.Router.extend({

		routes : {
			'sys/acc(/:username)' : 'account'
		},
		setOptions : function(options) {
			$container = options.$container;

			layoutView = new LayoutView({
				el : $container
			});

			frameView = options.frameView;
		},
		account : function(username) {
			layoutView.render();
			$list = $('.sidebar', layoutView.el);
			$main = $('.main', layoutView.el);

			require([ 'views/system/account/list', 'views/system/account/detail' ], function(
					ListView, DetailView) {
				var listView = new ListView({
					el : $list
				}).render();
				var detailView = new DetailView({
					el : $main
				}).render({
					username : username,
					listView : listView
				});
			});

			frameView.selectMenuItem('system');
		}

	});

});