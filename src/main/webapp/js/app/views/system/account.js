define(function(require) {

	"use strict";

	var $ = require('jquery'), _ = require('underscore'), Backbone = require('backbone');
	// AccountListItemView = require('views/system/accountListItem');

	return Backbone.View.extend({

		initialize : function() {
			console.log('account.js');
		},

		render : function() {
			return this;
		}
	});

});
