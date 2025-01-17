package org.nekomanga.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import org.nekomanga.presentation.components.Loading

@Composable
fun LoadingScreen(contentPadding: PaddingValues = PaddingValues()) {
    Box(modifier = Modifier.fillMaxSize()) {
        Loading(
            Modifier
                .zIndex(1f)
                .padding(8.dp)
                .padding(top = contentPadding.calculateTopPadding())
                .align(Alignment.TopCenter),
        )
    }
}
