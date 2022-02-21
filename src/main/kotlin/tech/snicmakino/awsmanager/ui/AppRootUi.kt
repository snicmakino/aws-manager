package tech.snicmakino.awsmanager.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import tech.snicmakino.awsmanager.component.AppRoot
import tech.snicmakino.awsmanager.ui.common.CredentialSelector

@Composable
fun AppRootUi(component: AppRoot) {
    Children(component.routerState) {
        Column {
            CredentialSelector(
                onSwitchCredential = component::onSwitchCredential,
                component = component.credentialManage
            )
            when (val child = it.instance) {
                is AppRoot.Child.Ec2Content -> Ec2Ui(child.component)
            }
        }
    }
}