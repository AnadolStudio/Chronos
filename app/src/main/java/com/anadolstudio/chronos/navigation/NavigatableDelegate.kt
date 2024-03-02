package com.anadolstudio.chronos.navigation

import androidx.navigation.NavController
import com.anadolstudio.ui.Navigatable
import com.anadolstudio.ui.navigation.Add
import com.anadolstudio.ui.navigation.Back
import com.anadolstudio.ui.navigation.BackTo
import com.anadolstudio.ui.navigation.NavigationEvent
import com.anadolstudio.ui.navigation.Replace

class NavigatableDelegate(private val navController: NavController) : Navigatable<NavigateData> {

    override fun handleNavigationEvent(event: NavigationEvent<NavigateData>) {
        try {
            navigate(event)
        } catch (ex: Exception) {
            ex.printStackTrace()
            // TODO
        }
    }

    private fun navigate(event: NavigationEvent<NavigateData>) {
        when (event) {
            is Replace -> navController.navigate(event.data.id, event.data.args)
            is Add -> navController.navigate(event.data.id, event.data.args)
            is Back -> navController.navigateUp()
            is BackTo -> Unit
        }
    }
}
