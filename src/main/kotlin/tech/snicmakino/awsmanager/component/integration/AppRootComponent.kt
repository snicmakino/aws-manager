package tech.snicmakino.awsmanager.component.integration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import tech.snicmakino.awsmanager.component.*
import tech.snicmakino.awsmanager.component.store.CredentialStoreProvider
import tech.snicmakino.awsmanager.domain.repository.ConfigurationRepository
import tech.snicmakino.awsmanager.repository.configuration.Credential

class AppRootComponent internal constructor(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    repository: ConfigurationRepository,
    override val credentialManage: CredentialManage,
    private val ec2: (ComponentContext) -> Ec2,
) : AppRoot, ComponentContext by componentContext {

    private val store =
        instanceKeeper.getStore {
            CredentialStoreProvider(
                storeFactory = storeFactory,
                configurationRepository = repository
            ).provide()
        }

    constructor(
        componentContext: ComponentContext,
        storeFactory: StoreFactory,
        repository: ConfigurationRepository,
    ) : this(
        componentContext = componentContext,
        storeFactory = storeFactory,
        repository = repository,
        credentialManage = CredentialManageComponent(
            componentContext = componentContext,
            storeFactory = storeFactory,
            repository = repository,
        ),
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
    override fun onSwitchCredential(credential: Credential) {
//        _value.reduce { it.copy(credential = credential) }
    }

    private fun createChild(config: Configuration, componentContext: ComponentContext): AppRoot.Child =
        when (config) {
            is Configuration.Ec2 -> AppRoot.Child.Ec2Content(ec2(componentContext))
        }
}

private sealed class Configuration : Parcelable {
    @Parcelize
    object Ec2 : Configuration()
}
