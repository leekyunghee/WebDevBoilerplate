define(function(require) {

	"use strict";

	// require library
	var $ = require('jquery'), Backbone = require('backbone');

	// require View
	var LayoutView = require('views/layout'), layoutView, frameView;

	var $container, $list, $content;

	return Backbone.Router.extend({

		routes : {
			'pol/vdp(/:ruleNo)' : 'detection'
		},
		setOptions : function(options) {
			$container = options.$container;

			layoutView = new LayoutView({
				el : $container
			});
			$list = $('.list', layoutView.el);
			$content = $('.content', layoutView.el);
			
			frameView = options.frameView;
		},
		detection : function(ruleNo) {
			layoutView.render();
			frameView.selectMenuItem('policy');
		}

	});

});