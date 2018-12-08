package com.example.matej.arkanoid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LostSceen extends Activity {

    EditText name;
    TextView score;
    Button butMain;
    Button butSend;
    Intent intent;
    String value;
    String string;
    boolean write = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_sceen);
        intent = getIntent();
        value = intent.getStringExtra("score");

        string = "Your score: " + value;

        score = (TextView)findViewById(R.id.score);
        butMain = (Button)findViewById(R.id.button2);
        butSend = (Button)findViewById(R.id.sendScore);
        name = (EditText)findViewById(R.id.editText);

        score.setText(string);

    }

    public void sendScore(View view) {
        Intent intent = new Intent(this, Leaderboards.class);
        intent.putExtra("write", write);
        intent.putExtra("name", name.getText().toString());
        intent.putExtra("score", value);
        startActivity(intent);
    }

    public void goBack(View view) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}
