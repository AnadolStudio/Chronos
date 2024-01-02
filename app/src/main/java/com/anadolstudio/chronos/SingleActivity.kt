package com.anadolstudio.chronos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anadolstudio.chronos.databinding.ActivityMainBinding
import com.anadolstudio.core.presentation.activity.CoreActivity

class SingleActivity : CoreActivity() {

    companion object {
        fun start(context: Context) = context.startActivity(Intent(context, SingleActivity::class.java))
    }

    private val binding by lazy(LazyThreadSafetyMode.NONE) { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}
