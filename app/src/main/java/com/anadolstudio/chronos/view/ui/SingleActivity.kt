package com.anadolstudio.chronos.view.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anadolstudio.chronos.R

class SingleActivity : AppCompatActivity() {

    companion object {

        fun start(context: Context) {
            context.startActivity(
                Intent(
                    context,
                    SingleActivity::class.java
                )
            )
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_container)
    }
}
