package com.example.matej.arkanoid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.security.DigestInputStream;

public class MainMenu extends Activity {

    Button butExit;
    Button butStart;
    Button butSett;
    Button butLead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        butExit = (Button)findViewById(R.id.exit);
        butLead = (Button)findViewById(R.id.leaderB);
        butSett = (Button)findViewById(R.id.settings);
        butStart = (Button)findViewById(R.id.play);

    }

    public void exitApp(View view) {
        finish();
        System.exit(0);
    }

    public void startGame(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void leaderB(View view){
        Intent intent = new Intent(this, Leaderboards.class);
        startActivity(intent);
    }
}
