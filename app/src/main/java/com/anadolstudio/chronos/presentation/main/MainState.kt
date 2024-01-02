package com.anadolstudio.chronos.presentation.main

import org.joda.time.DateTime

data class MainState(
    val currentData: DateTime = DateTime.now()
)
