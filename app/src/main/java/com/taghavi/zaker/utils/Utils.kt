package com.taghavi.zaker.utils

import android.content.Context
import android.graphics.Typeface
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.taghavi.zaker.R

@Suppress("DEPRECATION")
class Utils {
    companion object {
        fun clickAnimation(context: Context): Animation {
            return AnimationUtils.loadAnimation(context, R.anim.anim_click)
        }

        fun setFont(context: Context): Typeface {
            return Typeface.createFromAsset(context.assets, "fonts/iranian_sans.ttf")
        }
    }
}