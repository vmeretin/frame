package io.github.kmeret.frame.infrastructure.presentation.extensions

import android.graphics.PorterDuff
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide

fun AppCompatImageView.setIcon(@DrawableRes iconRes: Int) {
    setImageDrawable(ContextCompat.getDrawable(context, iconRes))
}

fun AppCompatImageView.setColor(@ColorRes colorRes: Int) {
    setColorFilter(ContextCompat.getColor(context, colorRes), PorterDuff.Mode.SRC_IN)
}

fun AppCompatImageView.loadByUrl(url: String) {
    Glide.with(context)
        .load(url)
        .into(this)
}
