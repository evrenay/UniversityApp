package com.evren.core.extensions

import android.content.Context
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.evren.core.utils.ClearFocusQueryTextListener

/**
 * Shorthand for [contains] with ignoreCase set [true]
 */
fun CharSequence.containsIgnoreCase(other: CharSequence) = contains(other, true)

/**
 * Adds [ClearFocusQueryTextListener] as [SearchView.OnQueryTextListener]
 */
fun SearchView.setOnQueryChangedListener(block: (String?) -> Unit) = setOnQueryTextListener(
    ClearFocusQueryTextListener(this, block)
)

/**
 * Shortening set menu item expands / collapses
 */
fun MenuItem.onExpandOrCollapse(onExpand: () -> Unit, onCollapse: () -> Unit) {
    setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
        override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
            onCollapse()
            return true
        }

        override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
            onExpand()
            return true
        }
    })
}

fun Fragment.hideKeyboard() {
    try {
        val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        activity?.currentFocus?.let {
            it.clearFocus()
            inputManager.hideSoftInputFromWindow(
                it.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    } catch (e: Exception) {}
}
