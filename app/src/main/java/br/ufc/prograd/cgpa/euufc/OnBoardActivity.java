package br.ufc.prograd.cgpa.euufc;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hololo.tutorial.library.Step;
import com.hololo.tutorial.library.TutorialActivity;

public class OnBoardActivity extends TutorialActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Slide 1
        addFragment(new Step.Builder().setTitle("This is header")
                .setContent("This is content")
                .setBackgroundColor(Color.parseColor("#14478F")) // int background color
                .setDrawable(R.drawable.botaocheck) // int top drawable
                .setSummary("This is summary")
                .build());

        // Slide 2
        addFragment(new Step.Builder().setTitle("This is header")
                .setContent("This is content")
                .setBackgroundColor(Color.parseColor("#3F51B5")) // int background color
                .setDrawable(R.drawable.botaocheck) // int top drawable
                .setSummary("This is summary")
                .build());

        // Slide 3
        addFragment(new Step.Builder().setTitle("This is header")
                .setContent("This is content")
                .setBackgroundColor(Color.parseColor("#1C60BF")) // int background color
                .setDrawable(R.drawable.botaocheck) // int top drawable
                .setSummary("This is summary")
                .build());
    }
    @Override
    public void finishTutorial() {
        Intent intent = new Intent(
                OnBoardActivity.this,LoginActivity.class
        );
        startActivity(intent);
        finish();
    }
}
