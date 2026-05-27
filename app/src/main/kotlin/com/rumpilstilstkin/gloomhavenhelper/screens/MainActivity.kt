package com.rumpilstilstkin.gloomhavenhelper.screens

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.rumpilstilstkin.gloomhavenhelper.localization.AppLocaleManager
import com.rumpilstilstkin.gloomhavenhelper.localization.LocalContentLocale
import com.rumpilstilstkin.gloomhavenhelper.localization.LocalImageResolver
import com.rumpilstilstkin.gloomhavenhelper.localization.LocaleSource
import com.rumpilstilstkin.gloomhavenhelper.localization.LocalizedImageResolver
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperNavHost
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel: MainActivityViewModel by viewModels()

    @Inject
    lateinit var localeSource: LocaleSource

    @Inject
    lateinit var imageResolver: LocalizedImageResolver

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(AppLocaleManager.wrap(newBase))
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState.Loading)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .onEach { uiState = it }
                    .collect()
            }
        }

        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val fadeOut = ObjectAnimator.ofFloat(splashScreenView.view, View.ALPHA, 1f, 0f)
            fadeOut.duration = 200L
            fadeOut.doOnEnd { splashScreenView.remove() }
            fadeOut.start()
        }
        splashScreen.setKeepOnScreenCondition {
            when (uiState) {
                MainActivityUiState.Loading -> true
                is MainActivityUiState.Success -> false
            }
        }

        enableEdgeToEdge()
        setContent {
            val contentLocale by localeSource.locale.collectAsStateWithLifecycle()
            GloomhavenMasterTheme {
                CompositionLocalProvider(
                    LocalContentLocale provides contentLocale,
                    LocalImageResolver provides imageResolver,
                ) {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        GlHelperNavHost(innerPadding = innerPadding)
                    }
                }
            }
        }
    }
}