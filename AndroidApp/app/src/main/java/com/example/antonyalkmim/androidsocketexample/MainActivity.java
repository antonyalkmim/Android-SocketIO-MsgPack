package com.example.antonyalkmim.androidsocketexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.msgpack.jackson.dataformat.MessagePackFactory;

import java.io.IOException;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {

    private Socket socket;
    ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());

    //views
    private Button btBet;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setup socket API
        SocketApplication app = (SocketApplication) getApplication();
        socket = app.getSocket();

        //lifecycler events
        socket.on(Socket.EVENT_CONNECT, onConnect);
        socket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        socket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        socket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        //api events
        socket.on("new response", onNewResult);
        socket.connect();

        //setup views
        textView = (TextView) findViewById(R.id.textView);
        btBet = (Button) findViewById(R.id.btBet);
        btBet.setOnClickListener(v -> {
            try {
                User user = new User("Antony", "Alkmim");
                byte[] bytes = objectMapper.writeValueAsBytes(user);
                socket.emit("new request", bytes);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.disconnect();

        socket.off(Socket.EVENT_CONNECT, onConnect);
        socket.off(Socket.EVENT_DISCONNECT, onDisconnect);
        socket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        socket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        //api events
        socket.off("new response", onNewResult);
    }

    /********************************
     *  Socket Events
     *******************************/
    private Emitter.Listener onConnect = args -> Log.d("MainAcivity", "Connected");
    private Emitter.Listener onDisconnect = args -> Log.d("MainAcivity", "onDisconnect");
    private Emitter.Listener onConnectError = args -> Log.d("MainAcivity", "onConnectError");

    private Emitter.Listener onNewResult = args -> runOnUiThread(() -> {
        try {
            User user = objectMapper.readValue((byte[]) args[0], User.class);
            textView.setText("Response:\nUsername: " + user.username + "\nEmail: " + user.email);
        } catch (IOException e) {
            e.printStackTrace();
        }
    });

}
