package com.anadolstudio.chronos.presentation.common

import android.os.Parcelable

abstract class BaseNavigationArgs(
        open val requestKey: String,
) : Parcelable
