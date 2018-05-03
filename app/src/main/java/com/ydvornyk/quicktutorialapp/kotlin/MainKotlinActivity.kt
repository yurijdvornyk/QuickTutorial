package com.ydvornyk.quicktutorialapp.kotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import com.ydvornyk.quicktutorial.QuickTutorial
import com.ydvornyk.quicktutorialapp.R

class MainKotlinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle(R.string.sample_app)
        val button: Button = findViewById(R.id.button)
        button.setOnClickListener({ (application as QuickTutorialKotlinApplication).buildAndStartTutorial(this) })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == QuickTutorial.REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> Toast.makeText(this, "Completed", Toast.LENGTH_LONG).show()
                Activity.RESULT_CANCELED -> Toast.makeText(this, "Dismissed", Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
