package com.ydvornyk.appjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ydvornyk.quicktutorial.QuickTutorial;
import com.ydvornyk.quicktutorial.model.TutorialContent;
import com.ydvornyk.quicktutorial.model.TutorialPage;

public class MainJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.sample_app_name_java);
        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTutorial();
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

    private void showTutorial() {
        new QuickTutorial.Builder()
                .withContent(new TutorialContent()
                        .addPage(new TutorialPage.Builder()
                                .title(getString(R.string.tutorial_title_page0))
                                .addText(getString(R.string.tutorial_text_page0))
                                .build())
                        .addPage(new TutorialPage.Builder()
                                .title(getString(R.string.tutorial_title_page1))
                                .addText(getString(R.string.tutorial_text_page1))
                                .build())
                        .addPage(new TutorialPage.Builder()
                                .title(getString(R.string.tutorial_title_page2))
                                .addDrawable(R.drawable.ic_chart)
                                .build())
                        .addPage(new TutorialPage.Builder()
                                .title(getString(R.string.tutorial_title_page3))
                                .addText(getString(R.string.tutorial_text_page3))
                                .addDrawable(R.drawable.ic_norway)
                                .build())
                        .addPage(new TutorialPage.Builder()
                                .addLayout(R.layout.layout_page4)
                                .build())
                )
                .build()
                .start(this);
    }
}
