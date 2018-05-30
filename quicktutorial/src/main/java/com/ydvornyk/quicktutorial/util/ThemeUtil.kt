package com.ydvornyk.quicktutorial.util

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.support.annotation.AttrRes

class ThemeUtil {

    companion object {

        fun getStyleableDrawable(context: Context, @AttrRes attribute: Int): Drawable {
            val attrs = intArrayOf(attribute)
            val typedArray: TypedArray = context.obtainStyledAttributes(attrs)
            val drawable = typedArray.getDrawable(0)
            typedArray.recycle()
            return drawable
        }
    }
}