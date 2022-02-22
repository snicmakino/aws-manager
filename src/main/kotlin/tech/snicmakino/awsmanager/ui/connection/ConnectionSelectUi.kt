package tech.snicmakino.awsmanager.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tech.snicmakino.awsmanager.component.ConnectionComponent
import tech.snicmakino.awsmanager.ui.common.CredentialSelector

@Composable
fun ConnectionUi(component: ConnectionComponent) {
    Column(
        modifier = Modifier.padding(top = 10.dp)
    ) {
        CredentialSelector(
            component = component
        )
    }
}
