package org.calebetadeu.streamingtowatch

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import gymmatekmp.composeapp.generated.resources.Res
import gymmatekmp.composeapp.generated.resources.compose_multiplatform
import org.calebetadeu.streamingtowatch.presentation.screen.GymMateScreen
import org.calebetadeu.streamingtowatch.presentation.viewModel.GymMateViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Route.GymMate
        ) {
            composable<Route.GymMate> {
                val viewModel = koinViewModel<GymMateViewModel>()
                GymMateScreen(
                    gymMateViewModel = viewModel
                )
            }

        }
    }
}


