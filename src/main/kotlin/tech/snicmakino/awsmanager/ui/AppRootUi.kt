package tech.snicmakino.awsmanager.ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import tech.snicmakino.awsmanager.component.AppRoot

@Composable
fun AppRootUi(component: AppRoot) {
    Children(component.routerState) {
        when (val child = it.instance) {
            is AppRoot.Child.Ec2Content -> Ec2Ui(child.component)
        }
    }
}
