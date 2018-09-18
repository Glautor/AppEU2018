package br.ufc.prograd.cgpa.euufc;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mostrarMainActivity();
            }
        }, 15000);

    }

    private void mostrarMainActivity() {
        Intent intent = new Intent(
                MainActivity.this,LoginActivity.class
        );
        startActivity(intent);
        finish();
    }
}
