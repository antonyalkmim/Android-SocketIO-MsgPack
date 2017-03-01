var app = require('express')();  
var http = require('http').Server(app);  
var io = require('socket.io')(http);


io.on('connection', (client) => {  
	
	// io.use(function(socket, next){
	//   if (socket.request.headers.cookie) return next();
	//   next(new Error('Authentication error'));
	// });

	client.on('new request', (data) => {  
		client.emit('new response', "Ping");
	});

});



http.listen(3000, () => console.log('server running on localhost:3000'));