package org.calebetadeu.streamingtowatch

import androidx.compose.ui.window.ComposeUIViewController
import org.calebetadeu.streamingtowatch.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }

) { App() }