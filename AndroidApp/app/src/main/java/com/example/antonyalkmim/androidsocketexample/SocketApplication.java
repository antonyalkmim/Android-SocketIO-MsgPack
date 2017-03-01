package com.example.antonyalkmim.androidsocketexample;

import android.app.Application;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;


public class SocketApplication extends Application {

    private Socket socket;
    {
        try {
            socket = IO.socket("http://192.168.0.12:3000");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return socket;
    }

}
