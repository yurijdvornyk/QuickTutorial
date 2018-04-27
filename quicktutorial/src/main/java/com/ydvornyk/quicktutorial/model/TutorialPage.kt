package com.ydvornyk.quicktutorial.model

import android.os.Parcelable
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import kotlinx.android.parcel.Parcelize

/**
 * Created by yuriidvornyk on 4/17/18.
 */
@Parcelize
class TutorialPage(
        var title: String,
        @DrawableRes var imageRes: Int,
        @LayoutRes var layoutRes: Int,
        var text: String)
    : Parcelable {

    private constructor() : this("", -1, -1, "")

    class Builder {

        private val page: TutorialPage = TutorialPage()

        fun title(title: String): Builder {
            page.title = title
            return this
        }

        fun text(text: String): Builder {
            page.text = text
            return this
        }

        fun image(@DrawableRes imageRes: Int): Builder {
            page.imageRes = imageRes
            return this
        }

        fun layout(@LayoutRes layoutRes: Int): Builder {
            page.layoutRes = layoutRes
            return this
        }

        fun build(): TutorialPage {
            return page
        }
    }
}