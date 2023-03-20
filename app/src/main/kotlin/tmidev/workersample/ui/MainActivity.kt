package tmidev.workersample.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import tmidev.workersample.ui.theme.AppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val viewModel = hiltViewModel<MainActivityViewModel>()

            val isOnline by viewModel.isOnline.collectAsStateWithLifecycle()
            val isLogWorkEnqueued by viewModel.isLogWorkEnqueued.collectAsStateWithLifecycle()
            val isLogWorkRunning by viewModel.isLogWorkRunning.collectAsStateWithLifecycle()
            val logs by viewModel.logs.collectAsStateWithLifecycle()

            AppTheme {
                MainContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding(),
                    isOnline = isOnline,
                    isLogWorkEnqueued = isLogWorkEnqueued,
                    isLogWorkRunning = isLogWorkRunning,
                    logs = logs
                )
            }
        }
    }
}