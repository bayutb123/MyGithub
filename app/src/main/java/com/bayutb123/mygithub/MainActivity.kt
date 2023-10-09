package com.bayutb123.mygithub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.bayutb123.mygithub.presentation.App
import com.bayutb123.mygithub.ui.theme.MyGithubTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mode = applicationContext.resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
        val surContainer = if ( mode == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
            getColor(R.color.surfaceContainerDark)
        } else {
            getColor(R.color.surfaceContainer)
        }
        window.navigationBarColor = surContainer

        setContent {
            MyGithubTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
    }
}

