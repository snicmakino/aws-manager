package tech.snicmakino.awsmanager.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import tech.snicmakino.awsmanager.component.AppRootComponent

@Composable
fun AppRootUi(component: AppRootComponent) {
    Children(component.routerState) {
        Column {
            TopAppBar(
                title = { Text("Simple TopAppBar") },
                navigationIcon = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Rounded.Settings, contentDescription = null)
                    }
                },
                actions = {
                    // RowScope here, so these icons will be placed horizontally
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Filled.Favorite, contentDescription = "Localized description")
                    }
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Filled.Favorite, contentDescription = "Localized description")
                    }
                }
            )

            when (val child = it.instance) {
                is AppRootComponent.Child.Connection -> ConnectionUi(child.component)
                is AppRootComponent.Child.Ec2Content -> Ec2Ui(child.component)
            }
        }
    }
}
