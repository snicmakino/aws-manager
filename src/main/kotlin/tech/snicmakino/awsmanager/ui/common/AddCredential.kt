package tech.snicmakino.awsmanager.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Dialog

@Composable
internal fun AddCredential(
    onCloseClicked: () -> Unit,
    onAddCredential: (String, String, String) -> Unit
) {
    val name = remember { mutableStateOf("") }
    val key = remember { mutableStateOf("") }
    val secret = remember { mutableStateOf("") }
    Dialog(
        title = "Add Connection",
        onCloseRequest = onCloseClicked,
    ) {
        Column {
            TextField(
                value = name.value,
                onValueChange = { name.value = it },
                label = { Text(text = "name") },
                singleLine = true
            )
            TextField(
                value = key.value,
                onValueChange = { key.value = it },
                label = { Text(text = "key") },
                singleLine = true
            )
            TextField(
                value = secret.value,
                onValueChange = { secret.value = it },
                label = { Text(text = "secret") },
                singleLine = true
            )
            Button(
                onClick = {
                    onAddCredential(name.value, key.value, secret.value)
                }
            ) { Text("Add") }
        }
    }
}
