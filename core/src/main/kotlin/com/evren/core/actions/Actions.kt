package com.evren.core.actions

import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.evren.core.R

/**
 * Navigation actions for navigation between feature activities
 */
fun Fragment.openDetail(id: Int) =
    NavHostFragment.findNavController(this).navigate(Uri.parse(getString(R.string.detail_uri, id)))

fun Fragment.openAuthentication(id: Int) =
    NavHostFragment.findNavController(this).navigate(Uri.parse(getString(R.string.authentication_uri, id)))