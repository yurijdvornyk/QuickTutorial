package com.ydvornyk.quicktutorialapp.kotlin

import android.app.Application
import android.content.Context

import com.ydvornyk.quicktutorial.QuickTutorial
import com.ydvornyk.quicktutorial.model.TutorialContent
import com.ydvornyk.quicktutorial.model.TutorialPage
import com.ydvornyk.quicktutorialapp.R

/**
 * Created by yuriidvornyk on 4/16/18.
 */

class QuickTutorialKotlinApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        buildAndStartTutorial(this)
    }

    fun buildAndStartTutorial(context: Context) {
        QuickTutorial.Builder()
                .withContent(createContent())
                .allowSwipeNavigation(true)
                .build()
                .start(context)
    }

    private fun createContent(): TutorialContent {
        val content = TutorialContent()
        content.addPage(TutorialPage.Builder()
                .title("Welcome")
                .text("Hello!\nWelcome to this QuickTutorial sample.\nLet's overview its main features!")
                .build())
        content.addPage(TutorialPage.Builder()
                .title("Say hi to Shaldon")
                .image(R.drawable.ic_sheldon)
                .build())
        content.addPage(TutorialPage.Builder()
                .title("Something more complex")
                .layout(R.layout.layout_tutorial_slide2)
                .build())
        content.addPage(TutorialPage.Builder()
                .title("Ok, let's complete it all")
                .layout(R.layout.layout_tutorial_slide3)
                .build())
        return content
    }
}
