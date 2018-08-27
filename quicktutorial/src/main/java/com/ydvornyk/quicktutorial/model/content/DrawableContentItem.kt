package com.ydvornyk.quicktutorial.model.content

import android.support.annotation.DrawableRes

class DrawableContentItem(@DrawableRes drawable: Int) : ContentItem(drawable, ContentItemType.DRAWABLE)