package com.ydvornyk.quicktutorial.model.content

import android.support.annotation.LayoutRes

class LayoutContentItem(@LayoutRes layout: Int) : ContentItem(layout, ContentItemType.LAYOUT)