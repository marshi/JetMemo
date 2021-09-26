package dev.marshi.jetmemo.ui

import androidx.navigation.NavController

interface NavControllerWrapper {
    fun navigate(route: String)
    fun popBackStack()
}

class NavControllerWrapperImpl(
    private val navController: NavController
) : NavControllerWrapper {
    override fun navigate(route: String) {
        navController.navigate(route)
    }

    override fun popBackStack() {
        navController.popBackStack()
    }
}

class NavControllerWrapperForPreview(
) : NavControllerWrapper {
    override fun navigate(route: String) {
        // do nothing
    }

    override fun popBackStack() {
       // do nothing
    }
}