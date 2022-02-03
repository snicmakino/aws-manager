package tech.snicmakino.awsmanager.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

class AppRootComponent internal constructor(
    componentContext: ComponentContext,
    private val ec2: (ComponentContext) -> Ec2,
) : AppRoot, ComponentContext by componentContext {

    constructor(
        componentContext: ComponentContext
    ) : this(
        componentContext = componentContext,
        ec2 = { childContext ->
            Ec2Component(
                componentContext = childContext,
            )
        }
    )

    private val router = router<Configuration, AppRoot.Child>(
        initialConfiguration = Configuration.Ec2,
        handleBackButton = false,
        childFactory = ::createChild
    )

    override val routerState: Value<RouterState<*, AppRoot.Child>> = router.state

    private fun createChild(config: Configuration, componentContext: ComponentContext): AppRoot.Child =
        when (config) {
            is Configuration.Ec2 -> AppRoot.Child.Ec2Content(ec2(componentContext))
        }
}

private sealed class Configuration : Parcelable {
    @Parcelize
    object Ec2 : Configuration()
}
