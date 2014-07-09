require.config({

	baseUrl : 'js/lib',

	paths : {
		app : '../app',
		views : '../app/views',
		models : '../app/models',
		tpl : '../tpl'
	},

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
		}
	}
});

require([ 'jquery', 'backbone', 'app/router' ], function($, Backbone, Router) {
	console.log('start app');
	var router = new Router();
	Backbone.history.start();
});