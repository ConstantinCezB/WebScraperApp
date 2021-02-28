package com.constantin.webscraperapp.util

import android.graphics.drawable.Drawable
import android.view.MenuItem
import androidx.annotation.StringRes

fun MenuItem.changeMenu(
    drawableRes: Drawable,
    @StringRes resId: Int
) {
    icon = drawableRes
    setTitle(resId)
}