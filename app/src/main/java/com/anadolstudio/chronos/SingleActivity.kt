package com.anadolstudio.chronos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SingleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_container)
    }
}