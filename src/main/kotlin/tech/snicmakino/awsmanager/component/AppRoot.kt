package tech.snicmakino.awsmanager.component

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value

interface AppRoot {
    val routerState: Value<RouterState<*, Child>>

    sealed class Child {
        data class Ec2Content(val component: Ec2): Child()
    }
}
