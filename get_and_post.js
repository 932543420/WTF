var express = require('express');

var app = express();
var handlebars = require('express-handlebars').create({defaultLayout:'main'});
var bodyParser = require('body-parser');

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

app.engine('handlebars', handlebars.engine);
app.set('view engine', 'handlebars');
app.set('port', 3001);

app.get('/', function(req, res){
	var queryData = [];
	
	for(var p in req.query)
	{
		queryData.push({'name':p, 'value':req.query[p]});
	}
	
	var context = {};
	context.queryArray = queryData;
	res.render('get', context);	
	
});

app.post('/', function(req, res){
	var queryBodyData = [];
	var queryQueryData = [];
	
	for(var p in req.body)
	{
		queryBodyData.push({'name':p, 'value':req.body[p]});
	}
	
	for(var p in req.query)
	{
		queryQueryData.push({'name':p, 'value':req.query[p]});
	}
	
	var context = {};
	context.queryBody = queryBodyData;
	context.queryQuery = queryQueryData;
	res.render('post', context);	
});

app.use(function(req,res){
  res.status(404);
  res.render('404');
});

app.use(function(err, req, res, next){
  console.error(err.stack);
  res.type('plain/text');
  res.status(500);
  res.render('500');
});





app.listen(app.get('port'), function(){
  console.log('Express started on http://localhost:' + app.get('port') + '; press Ctrl-C to terminate.');
});
