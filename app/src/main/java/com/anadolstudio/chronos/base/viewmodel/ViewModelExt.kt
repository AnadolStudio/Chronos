package com.anadolstudio.chronos.base.viewmodel

import android.os.Bundle
import androidx.core.os.bundleOf
import com.anadolstudio.chronos.navigation.NavigateData
import com.anadolstudio.ui.navigation.Back
import com.anadolstudio.ui.navigation.NavigationEvent
import com.anadolstudio.ui.navigation.Replace
import com.anadolstudio.ui.viewmodel.livedata.SingleLiveEvent
import com.anadolstudio.ui.viewmodel.livedata.onNext

fun SingleLiveEvent<NavigationEvent<NavigateData>>.navigateUp() = onNext(Back())

fun SingleLiveEvent<NavigationEvent<NavigateData>>.navigateTo(
         id: Int,
         args: Bundle = bundleOf()
) = onNext(Replace(NavigateData(id, args)))
