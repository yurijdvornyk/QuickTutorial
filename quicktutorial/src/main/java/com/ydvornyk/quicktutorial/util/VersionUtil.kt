package com.ydvornyk.quicktutorial.util

import android.os.Build

class VersionUtil {

    companion object {

        fun isLollipopOrHigher(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
        }
    }
}