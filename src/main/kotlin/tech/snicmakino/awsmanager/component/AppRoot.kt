package tech.snicmakino.awsmanager.component

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import tech.snicmakino.awsmanager.repository.configuration.Credential

interface AppRoot {
    val routerState: Value<RouterState<*, Child>>

    val credential: Value<Credential>

    fun onSwitchCredential(credential: Credential)

    fun onAddCredential(name: String, key: String, secret: String)

    sealed class Child {
        data class Ec2Content(val component: Ec2) : Child()
    }
}
