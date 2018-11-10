package com.example.matej.arkanoid;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    private ArkanoidView arkanoidView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(arkanoidView);
    }
}
