package com.ydvornyk.quicktutorial.model

import android.content.Context
import android.graphics.Color
import android.os.Parcelable
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import com.ydvornyk.quicktutorial.model.content.*
import kotlinx.android.parcel.Parcelize

/**
 * Created by yuriidvornyk on 4/17/18.
 */
@Parcelize
class TutorialPage(
        var title: CharSequence? = null,
        @ColorInt var backgroundColor: Int = -1,
        @DrawableRes var backgroundDrawable: Int = -1,
        @ColorInt var foregroundColor: Int = -1,
        var contentOrientation: ContentOrientation = ContentOrientation.VERTICAL,
        var content: MutableList<ContentItem>
) : Parcelable {

    private constructor() : this("", -1, -1, -1,
            ContentOrientation.VERTICAL, mutableListOf())

    enum class ContentOrientation {
        HORIZONTAL,
        VERTICAL
    }

    class Builder {

        private val page: TutorialPage = TutorialPage()

        fun title(title: CharSequence): Builder {
            page.title = title
            return this
        }

        fun backgroundColor(hex: String): Builder {
            page.backgroundColor = Color.parseColor(hex)
            return this
        }


        fun backgroundColor(@ColorRes colorRes: Int, context: Context): Builder {
            page.backgroundColor = ContextCompat.getColor(context, colorRes)
            return this
        }

        fun backgroundDrawable(@DrawableRes drawable: Int): Builder {
            page.backgroundDrawable = drawable
            return this
        }

        fun foregroundColor(@ColorRes colorRes: Int, context: Context): Builder {
            page.foregroundColor = ContextCompat.getColor(context, colorRes)
            return this
        }

        fun foregroundColor(hex: String): Builder {
            page.foregroundColor = Color.parseColor(hex)
            return this
        }

        fun contentOrientation(contentOrientation: ContentOrientation): Builder {
            page.contentOrientation = contentOrientation
            return this
        }

        fun addText(text: String): Builder {
            page.content.add(TextContentItem(text))
            return this
        }

        fun addImage(image: ByteArray): Builder {
            page.content.add(ImageContentItem(image))
            return this
        }

        fun addDrawable(@DrawableRes drawable: Int): Builder {
            page.content.add(DrawableContentItem(drawable))
            return this
        }

        fun addLayout(@LayoutRes layout: Int): Builder {
            page.content.add(LayoutContentItem(layout))
            return this
        }

        fun build(): TutorialPage {
            return page
        }
    }
}