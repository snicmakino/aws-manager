package tech.snicmakino.awsmanager.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import tech.snicmakino.awsmanager.component.ConnectionComponent

@Composable
fun CredentialSelector(
    component: ConnectionComponent
) {
    val model by component.models.subscribeAsState()
    val (open, setOpen) = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.padding(all = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Dropdown(
                credentials = model.credentials,
                onSelect = component.onCredentialSelect
            )
            Spacer(Modifier.width(10.dp))
            Button(
                onClick = { setOpen(true) },
                modifier = Modifier.height(32.dp)
            ) {
                if (open) {
                    AddCredential(
                        onCloseClicked = { setOpen(false) },
                        onAddCredential = component::addCredential
                    )
                }
                Text(
                    text = "Add",
                    fontSize = 14.sp,
                )
            }
        }
    }
}