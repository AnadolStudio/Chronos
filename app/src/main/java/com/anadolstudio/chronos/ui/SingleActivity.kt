package com.anadolstudio.chronos.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.ActivityMainBinding

class SingleActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) = context.startActivity(Intent(context, SingleActivity::class.java))
    }

    private val binding by lazy(LazyThreadSafetyMode.NONE) { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.content.bottomNavigationView.apply {
            menu.findItem(R.id.daysFragment).isChecked = true
            setupWithNavController(navigateController())
        }
    }

    private fun navigateController(): NavController = findNavController(R.id.nav_host_fragment_content_main)
}
