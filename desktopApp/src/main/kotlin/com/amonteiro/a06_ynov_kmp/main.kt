package com.amonteiro.a06_ynov_kmp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "A06_ynov_kmp",
    ) {
        App()
    }
}