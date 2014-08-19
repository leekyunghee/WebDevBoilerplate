define(function(require) {

	"use strict";

	// require library
	var $ = require('jquery'), Backbone = require('backbone');
	
	// require routers
	var DefaultRouter = require('routers/defaultRouter'), AccountRouter = require('routers/accountRouter');
	
	var defaultRouter = new DefaultRouter();
	var accountRouter = new AccountRouter();
});