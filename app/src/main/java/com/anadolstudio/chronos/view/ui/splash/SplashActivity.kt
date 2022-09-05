package com.anadolstudio.chronos.view.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anadolstudio.chronos.view.ui.SingleActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SingleActivity.start(this)
        finish()
    }
}
