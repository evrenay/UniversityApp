package com.evren.core.extensions

import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.evren.core.R
import com.evren.core.utils.GlideApp
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Loads image in given [url] to this [ImageView]
 *
 * @param url url of image
 */
@BindingAdapter(value= ["android:src", "android:placeHolder"],requireAll = false)
fun setImageUrl(imageView: CircleImageView, url: String?, placeHolder : Drawable?=null) {
    if (!TextUtils.isEmpty(url)) {
        val ph = placeHolder ?: ContextCompat.getDrawable(imageView.context, R.drawable.progress_animation)

        GlideApp.with(imageView.context)
            .load(url)
            .placeholder(ph)
            .error(ph)
            .fallback(ph)
            .into(imageView)
    } else {
        imageView.setImageDrawable(null)
    }
}
@BindingAdapter(value= ["android:src", "android:placeHolder"],requireAll = false)
fun setImageUrl(imageView: ImageView, url: String?, placeHolder : Drawable?=null) {
    if (!TextUtils.isEmpty(url)) {
        val ph = placeHolder ?: ContextCompat.getDrawable(imageView.context, R.drawable.progress_animation)

        GlideApp.with(imageView.context)
            .load(url)
            .placeholder(ph)
            .error(ph)
            .fallback(ph)
            .into(imageView)
    } else {
        imageView.setImageDrawable(null)
    }
}

@BindingAdapter("setError")
fun EditText.setError(errorStr : MutableLiveData<String>?) {
    errorStr?.value?.let {
        this.error = it
    }
}
