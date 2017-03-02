var app = require('express')();  
var http = require('http').Server(app);  
var io = require('socket.io')(http);

var msgpack = require("msgpack");

io.on('connection', (client) => {  
	
	// client.use((socket, next) => {
	// 	if (socket.request.headers.cookie) 
	// 		return next();
	  
	//   next(new Error('Authentication error'));
	// });

	client.on('new user request', (data) => {  
		var obj = msgpack.unpack(data);
		
		console.log("Received Data: ", data);
		console.log("username: ", obj.username);
		console.log("email: ", obj.email);
		
		var packedData = msgpack.pack({
			"username" : obj.email,
			"email": obj.username
		});

		console.log("Packed Data: ", packedData);
		
		client.emit('new user response', packedData);
	});

});



http.listen(3000, () => console.log('server running on localhost:3000'));