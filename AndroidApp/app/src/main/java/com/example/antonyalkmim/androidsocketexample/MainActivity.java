package com.example.antonyalkmim.androidsocketexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.antonyalkmim.androidsocketexample.models.User;
import com.example.antonyalkmim.androidsocketexample.network.ApiEvent;
import com.example.antonyalkmim.androidsocketexample.network.ApiService;
import com.example.antonyalkmim.androidsocketexample.network.PackUtils;

public class MainActivity extends AppCompatActivity {

    private ApiService apiService;

    //views
    private Button btBet;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //api events
        apiService = ((SocketApplication) getApplication()).getDefaultApi();
        apiService
                .addEventListener(ApiEvent.newUserAdded, data -> {
                    User user = PackUtils.decode(data, User.class);
                    textView.setText("Response:\nUsername: " + user.username + "\nEmail: " + user.email);
                })
                .connect();

        //setup views
        textView = (TextView) findViewById(R.id.textView);
        btBet = (Button) findViewById(R.id.btBet);
        btBet.setOnClickListener(v -> {
            User user = new User("Antony", "Alkmim");
            apiService.sendEvent(ApiEvent.newUser, PackUtils.encode(user));
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        apiService.close();
    }

}
