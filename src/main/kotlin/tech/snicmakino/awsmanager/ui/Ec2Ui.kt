package tech.snicmakino.awsmanager.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import tech.snicmakino.awsmanager.component.Ec2
import tech.snicmakino.awsmanager.ui.common.CredentialSelector
import view.Ec2Manager

@Composable
fun Ec2Ui(component: Ec2) {
    Column {
        CredentialSelector()
        Ec2Manager()
    }
}
