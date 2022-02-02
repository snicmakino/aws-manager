import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import view.AddCredential
import view.Dropdown
import view.RootStore


@Composable
@Preview
fun App() {

    val model = remember { RootStore() }
    val state = model.state

    MaterialTheme {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(Modifier.size(20.dp))
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

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Sample",
    ) {
        App()
    }
}
