package com.anadolstudio.chronos.extensions

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun Fragment.navigate(@IdRes resId: Int) = findNavController().navigate(resId)
