package tech.snicmakino.awsmanager

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import tech.snicmakino.awsmanager.component.AppRootComponent
import tech.snicmakino.awsmanager.repository.configuration.ConfigurationRepositoryImpl
import tech.snicmakino.awsmanager.ui.AppRootUi

@ExperimentalDecomposeApi
fun main() {
//    overrideSchedulers(main = Dispatchers.Main::asScheduler)

    val lifecycle = LifecycleRegistry()
    val root = AppRootComponent(
        DefaultComponentContext(lifecycle = lifecycle),
        storeFactory = DefaultStoreFactory(),
        repository = ConfigurationRepositoryImpl
    )

    application {
        val windowState = rememberWindowState()
        LifecycleController(lifecycle, windowState)

        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = "Sample",
        ) {
            Surface(modifier = Modifier.fillMaxSize()) {
                MaterialTheme {
                    CompositionLocalProvider {
                        AppRootUi(root)
                    }
                }
            }
        }
    }
}
