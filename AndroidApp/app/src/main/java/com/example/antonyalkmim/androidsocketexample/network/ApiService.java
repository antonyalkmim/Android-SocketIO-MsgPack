package com.example.antonyalkmim.androidsocketexample.network;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.net.URISyntaxException;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ApiService {

    private final String TAG = "ApiService";

    private String apiAddress;

    private Socket socket;

    public ApiService(String apiAddress) {
        this.apiAddress = apiAddress;

        try {
            socket = IO.socket(apiAddress);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        socket.on(Socket.EVENT_CONNECT, onConnect);
        socket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        socket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        socket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
    }

    public void connect() {
        socket.connect();
    }

    public void close() {
        socket.disconnect();
        socket.off();
    }


    public ApiService addEventListener(ApiEvent event, OnEventListener callback) {
        socket.on(event.getEventId(), args -> new Handler(Looper.getMainLooper())
                .post(() -> callback.run((byte[]) args[0])));
        return this;
    }
    public void sendEvent(ApiEvent event, byte[] data) {
        socket.emit(event.getEventId(), data);
    }


    //Lifecycler Listeners

    private Emitter.Listener onConnect = args -> Log.d(TAG, "onConnect");
    private Emitter.Listener onDisconnect = args -> Log.d(TAG, "onDisconnect");
    private Emitter.Listener onConnectError = args -> Log.d(TAG, "onConnectError");


    public interface OnEventListener {
        void run(byte[] data);
    }
}
