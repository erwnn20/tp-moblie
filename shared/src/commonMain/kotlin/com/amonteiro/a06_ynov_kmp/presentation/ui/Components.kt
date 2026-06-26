package com.amonteiro.a06_ynov_kmp.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun MyError(modifier : Modifier = Modifier, errorMessage : String?){
    AnimatedVisibility (!errorMessage.isNullOrBlank()) {
        Text(text= errorMessage ?: "",
            modifier = modifier.background(MaterialTheme.colorScheme.error).fillMaxWidth().padding(4.dp),
            color = MaterialTheme.colorScheme.onError
        )
    }
}