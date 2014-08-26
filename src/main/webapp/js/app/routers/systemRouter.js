define(function(require) {

	"use strict";

	// require library
	var $ = require('jquery'), Backbone = require('backbone');

	// require View
	var LayoutView = require('views/layout'), layoutView, frameView, listView;

	var $container, $list, $main;

	return Backbone.Router.extend({

		routes : {
			'sys/acc' : 'account',
			'sys/acc/:username' : 'accountDetail'
		},
		setOptions : function(options) {
			$container = options.$container;

			layoutView = new LayoutView({
				el : $container
			});

			frameView = options.frameView;
		},
		account : function() {
			layoutView.render();
			$list = $('.sidebar', layoutView.el);
			$main = $('.main', layoutView.el);

			require([ 'views/system/account/list' ], function(ListView) {
				listView = new ListView({
					el : $list
				}).render();
			});

			frameView.selectMenuItem('system');
		},
		accountDetail : function(username) {
			require([ 'views/system/account/detail' ], function(DetailView) {
				var detailView = new DetailView({
					el : $main
				}).render({
					username : username,
					listView : listView
				});
			});
		}

	});

});