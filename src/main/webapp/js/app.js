require.config({

	baseUrl : 'js/lib',

	paths : {
		app : '../app',
		views : '../app/views',
		models : '../app/models',
		tpl : '../tpl',
		nls : '../nls',
		bootstrap : '../../bootstrap/js/bootstrap'
	},

	// Set the config for the i18n
	// locale : "en-us",

	map : {
		'*' : {
			'models/employee' : 'models/memory/employee'
		}
	},

	shim : {
		'backbone' : {
			deps : [ 'underscore', 'jquery' ],
			exports : 'Backbone'
		},
		'underscore' : {
			exports : '_'
		},
		'bootstrap' : {
			deps : [ 'jquery' ],
			exports : 'bootstrap'
		}
	}
});

require([ 'jquery', 'underscore', 'backbone', 'app/router', 'views/common/modal',
		'views/common/alert' ], function($, _, Backbone, Router, ModalView, AlertView) {
	// create common view
	Backbone.ModalView = ModalView;
	Backbone.AlertView = AlertView;
	
	// create common util

	console.log('start app');

	var router = new Router();
	Backbone.history.start();
});