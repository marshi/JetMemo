package dev.marshi.jetmemo.ui

import androidx.navigation.NavController

interface NavControllerWrapper {
    fun navigate(route: String)
}

class NavControllerWrapperImpl(
    private val navController: NavController
) : NavControllerWrapper {
    override fun navigate(route: String) {
        navController.navigate(route)
    }
}

class NavControllerWrapperForPreview(
) : NavControllerWrapper {
    override fun navigate(route: String) {
        // do nothing
    }
}