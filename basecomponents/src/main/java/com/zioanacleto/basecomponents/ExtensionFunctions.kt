package com.zioanacleto.basecomponents

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

/**
 *  Extension functions with utility methods
 */

fun String?.default(): String = this ?: ""

fun ImageView.loadImage(url: String?, placeHolder: Drawable? = null){
    url?.let{
        Glide
            .with(this)
            .load(it)
            .placeholder(placeHolder)
            .into(this)
    }
}

fun View?.show(){
    this?.visibility = View.VISIBLE
}

fun View?.hide(){
    this?.visibility = View.GONE
}

inline fun <T : Any> letOrRun(
    element: T?,
    block1: (T) -> Unit = {},
    block2: () -> Unit = {}
) = element?.let { block1(it) } ?: run { block2() }

inline fun <T : Any, F : Any> returningLetOrRun(
    element: T?,
    block1: (T) -> F,
    block2: () -> F
): F = element?.let { block1(it) } ?: run { block2() }

inline fun <T: Fragment> T.withArgs(
    argsBuilder: Bundle.() -> Unit): T =
    this.apply {
        arguments = Bundle().apply(argsBuilder)
    }

fun Activity.hideKeyboard() {
    val inputMethodManager =
        this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
}