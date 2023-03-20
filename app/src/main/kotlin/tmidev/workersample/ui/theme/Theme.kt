package tmidev.workersample.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import tmidev.workersample.util.isAndroid12OrUp

@Composable
fun AppTheme(
    systemUiController: SystemUiController = rememberSystemUiController(),
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    useDynamicColors: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        isAndroid12OrUp() && useDynamicColors -> {
            val context = LocalContext.current
            if (useDarkTheme) dynamicDarkColorScheme(context = context)
            else dynamicLightColorScheme(context = context)
        }

        useDarkTheme -> darkColorScheme()

        else -> lightColorScheme()
    }

    DisposableEffect(
        key1 = systemUiController,
        key2 = useDarkTheme,
        key3 = useDynamicColors
    ) {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = colorScheme.background.luminance() > 0.5,
            isNavigationBarContrastEnforced = false,
            transformColorForLightContent = { Color.Black.copy(alpha = 0.6F) }
        )

        onDispose { }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = Shapes(),
        typography = Typography(),
        content = { Surface(content = content) }
    )
}