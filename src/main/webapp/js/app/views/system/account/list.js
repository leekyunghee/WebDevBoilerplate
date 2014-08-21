define(function(require) {

	"use strict";

	// require library
	var $ = require('jquery'),
		_ = require('underscore'),
		Backbone = require('backbone'),
		tpl = require('text!tpl/system/account/accountList.html');
	
	var template = _.template(tpl);
	
	var $list, $detail;
	
	// require views
	// AccountListItemView = require('views/system/accountListItem');

	return Backbone.View.extend({

		initialize : function() {
			console.log('account/list.js');
		},

		render : function() {
			this.$el.empty();
			this.$el.html(template());
			$list = $('#list', this.el);
			$detail = $('#detail', this.el);
			
			return this;
		}
	});

});
