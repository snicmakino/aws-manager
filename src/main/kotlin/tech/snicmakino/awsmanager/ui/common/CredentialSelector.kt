package tech.snicmakino.awsmanager.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tech.snicmakino.awsmanager.component.RootStore

@Composable
fun CredentialSelector() {
    val model = remember { RootStore() }
    val state = model.state
    Box(
        modifier = Modifier.padding(all = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Dropdown()
            Spacer(Modifier.width(10.dp))
            Button(
                onClick = { model.onItemClicked(1L) },
                modifier = Modifier.height(32.dp)
            ) {
                state.editingItemId?.also { id ->
                    AddCredential(
                        onCloseClicked = model::onEditorCloseClicked
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