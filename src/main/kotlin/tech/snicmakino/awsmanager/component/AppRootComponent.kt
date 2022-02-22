package tech.snicmakino.awsmanager.component

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value

interface AppRootComponent {
    val routerState: Value<RouterState<*, Child>>

    sealed class Child {
        data class Connection(val component: ConnectionComponent) : Child()
        data class Ec2Content(val component: Ec2Component) : Child()
    }
}
