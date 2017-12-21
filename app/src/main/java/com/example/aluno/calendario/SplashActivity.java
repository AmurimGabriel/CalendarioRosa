package com.example.aluno.calendario;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity implements Runnable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.barSplash));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.barSplash));

        new Handler().postDelayed(this, 3000);
    }

    @Override
    public void run() {
        Toast.makeText(SplashActivity.this, "Bem-Vinda!! :)", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
