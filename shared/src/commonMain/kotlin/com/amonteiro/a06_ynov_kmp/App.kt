package com.amonteiro.a06_ynov_kmp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.amonteiro.a06_ynov_kmp.presentation.ui.screens.SearchScreen
import com.amonteiro.a06_ynov_kmp.presentation.ui.theme.AppTheme

@Composable
@Preview
fun App() {

    AppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            println("coucou")

            SearchScreen(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}