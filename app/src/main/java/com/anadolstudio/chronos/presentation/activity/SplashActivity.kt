package com.anadolstudio.chronos.presentation.activity

import android.annotation.SuppressLint
import android.os.Bundle
import com.anadolstudio.chronos.presentation.activity.single.SingleActivity
import com.anadolstudio.ui.activity.CoreActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : CoreActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SingleActivity.start(this)
        finish()
    }
}
