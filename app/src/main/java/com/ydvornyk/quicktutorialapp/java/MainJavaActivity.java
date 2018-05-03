package com.ydvornyk.quicktutorialapp.java;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ydvornyk.quicktutorial.QuickTutorial;
import com.ydvornyk.quicktutorialapp.R;

public class MainJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.sample_app);
        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((QuickTutorialJavaApplication) getApplication()).buildAndStartTutorial(MainJavaActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == QuickTutorial.Companion.getREQUEST_CODE()) {
            switch (resultCode) {
                case RESULT_OK:
                    Toast.makeText(this, "Completed", Toast.LENGTH_LONG).show();
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(this, "Dismissed", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
