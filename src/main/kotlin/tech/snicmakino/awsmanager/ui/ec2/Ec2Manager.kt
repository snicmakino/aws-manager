package view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Ec2Manager() {
    Column(
        modifier = Modifier.padding(top = 10.dp)
    ) {
        Ec2Row()
        Ec2Row()
        Ec2Row()
        Ec2Row()
        Ec2Row()
    }
}

@Composable
fun Ec2Row() {
    Row(
        modifier = Modifier.fillMaxWidth().border(1.dp, Color.Gray),
    ) {
        Item() { Text("vps") }
        Item() { Text("i-040d1a58d809ad92e") }
        Item() { Text("running") }
        Item() { Text("t2.small") }
        Item() { Text("2021/12/14 15:59 GMT+9") }
    }
}

@Composable
fun Item(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp)
            .height(48.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        content()
    }
}
