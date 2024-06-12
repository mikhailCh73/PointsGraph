package com.example.presentation.enums


enum class Screen {
    SPLASH,
    MAIN,
    GRAPHIC_DETAILS,
}

sealed class NavigationItem(val route: String) {
    data object Splash : NavigationItem(Screen.SPLASH.name)
    data object Main : NavigationItem(Screen.MAIN.name)
    data object GraphicDetails : NavigationItem(Screen.GRAPHIC_DETAILS.name)
}