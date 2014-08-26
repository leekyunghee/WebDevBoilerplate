require.config({

	baseUrl : 'js/lib',

	paths : {
		app : '../app',
		views : '../app/views',
		models : '../app/models',
		collections : '../app/collections',
		routers : '../app/routers',
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

require([ 'jquery', 'underscore', 'backbone', 'views/common/commonView', 'routers/router' ],
function(CommonView, Router) {
	console.log('start app');

	Backbone.history.start();
});
