package com.example.antonyalkmim.androidsocketexample;

import android.app.Application;

import com.example.antonyalkmim.androidsocketexample.network.ApiService;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;


public class SocketApplication extends Application {

    private ApiService mApiService;

    public ApiService getDefaultApi() {
        return mApiService;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApiService = new ApiService(getString(R.string.api_address));
    }
}
