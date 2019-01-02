package com.ydvornyk.appkotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import com.ydvornyk.quicktutorial.QuickTutorial
import com.ydvornyk.quicktutorial.model.TutorialContent
import com.ydvornyk.quicktutorial.model.TutorialPage

class MainKotlinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle(R.string.sample_app_name_kotlin)
        val button: Button = findViewById(R.id.button)
        button.setOnClickListener { showTutorial() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == QuickTutorial.REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> Toast.makeText(this, getString(R.string.completed), Toast.LENGTH_LONG).show()
                Activity.RESULT_CANCELED -> Toast.makeText(this, getString(R.string.dismissed), Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun showTutorial() {
        QuickTutorial.Builder()
                .withContent(TutorialContent()
                        .addPage(TutorialPage.Builder()
                                .title(getString(R.string.tutorial_title_page0))
                                .addText(getString(R.string.tutorial_text_page0))
                                .build())
                        .addPage(TutorialPage.Builder()
                                .title(getString(R.string.tutorial_title_page1))
                                .addText(getString(R.string.tutorial_text_page1))
                                .build())
                        .addPage(TutorialPage.Builder()
                                .title(getString(R.string.tutorial_title_page2))
                                .addDrawable(R.drawable.ic_chart)
                                .build())
                        .addPage(TutorialPage.Builder()
                                .contentOrientation(TutorialPage.ContentOrientation.HORIZONTAL)
                                .title(getString(R.string.tutorial_title_page3))
                                .addText(getString(R.string.tutorial_text_page3))
                                .addDrawable(R.drawable.ic_norway)
                                .addLayout(R.layout.layout_page4)
                                .build())
                        .addPage(TutorialPage.Builder()
                                .addLayout(R.layout.layout_page4)
                                .build())
                )
                .withTheme(R.style.LightRedTutorialTheme)
                .build()
                .start(this)
    }
}
