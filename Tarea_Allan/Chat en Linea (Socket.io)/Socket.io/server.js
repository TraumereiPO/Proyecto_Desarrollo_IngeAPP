const io= require('socket.io') (3000, {
    cors: {
     origin: "http://127.0.0.1:5500",
     methods: ["GET", "POST"],
     allowedHeaders: ["my-custom-header"],
     credentials: true
     }
});

io.on ('connection',(socket)=> {
    console.log ('Usuario Conectado');
    socket.emit('message','Hola Mundo');
    socket.on('disconnect', () =>{
        console.log('Usuario Desconectado');
    });
    socket.on('chatmsg', msg =>{
        io.emit('message',msg);
    })
})