package com.anadolstudio.chronos

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anadolstudio.core.presentation.activity.CoreActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : CoreActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SingleActivity.start(this)
        finish()
    }
}
