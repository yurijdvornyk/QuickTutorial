package com.ydvornyk.quicktutorialapp

import android.app.Application

import com.ydvornyk.quicktutorial.QuickTutorial
import com.ydvornyk.quicktutorial.model.TutorialContent
import com.ydvornyk.quicktutorial.model.TutorialPage

/**
 * Created by yuriidvornyk on 4/16/18.
 */

class QuickTutorialApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        QuickTutorial.Builder.create()
                .withContent(createContent())
                .allowBackPress(false)
                .allowSwipeNavigation(true)
                .useNumericProgress("@")
                .build()
                .start(this)
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
