define(function(require) {

	"use strict";

	// require library
	var $ = require('jquery'),
		_ = require('underscore'),
		Backbone = require('backbone'),
		tpl = require('text!tpl/system/account/accountList.html');

	var template = _.template(tpl);

	var $list, $detail;

	return Backbone.View.extend({

		initialize : function() {
			console.log('account/list.js');
		},
		listItemTemplate : _
				.template('<a href="#sys/acc/<%= username %>"><p class="list-item"><%= username %></p></a>'),
		render : function() {
			this.$el.empty();
			this.$el.html(template());

			return this;
		},
		selectAccountItem : function(username) {
			console.log(username);
		}
	});

});
