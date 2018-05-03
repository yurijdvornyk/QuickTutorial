package com.ydvornyk.appjava;

import android.app.Application;
import android.content.Context;

import com.ydvornyk.quicktutorial.QuickTutorial;
import com.ydvornyk.quicktutorial.model.TutorialContent;
import com.ydvornyk.quicktutorial.model.TutorialPage;

public class QuickTutorialJavaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        buildAndStartTutorial(this);
    }

    public void buildAndStartTutorial(Context context) {
        new QuickTutorial.Builder()
                .withContent(createContent())
                .allowSwipeNavigation(true)
                .build()
                .start(context);
    }

    private TutorialContent createContent() {
        final TutorialContent content = new TutorialContent();
        content.addPage(new TutorialPage.Builder()
                .title("Welcome")
                .text("Hello!\nWelcome to this QuickTutorial sample.\nLet's overview its main features!")
                .build());
        content.addPage(new TutorialPage.Builder()
                .title("Say hi to Shaldon")
                .image(R.drawable.ic_sheldon)
                .build());
        content.addPage(new TutorialPage.Builder()
                .title("Something more complex")
                .layout(R.layout.layout_tutorial_slide2)
                .build());
        content.addPage(new TutorialPage.Builder()
                .title("Ok, let's complete it all")
                .layout(R.layout.layout_tutorial_slide3)
                .build());
        return content;
    }
}
