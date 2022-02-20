package tech.snicmakino.awsmanager.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import tech.snicmakino.awsmanager.component.store.CredentialStore.Intent
import tech.snicmakino.awsmanager.component.store.CredentialStoreProvider
import tech.snicmakino.awsmanager.domain.repository.ConfigurationRepository
import tech.snicmakino.awsmanager.repository.configuration.Credential

class AppRootComponent internal constructor(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    repository: ConfigurationRepository,
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

    private val _value = MutableValue(Credential("", "", "", ""))
    override val credential: Value<Credential> = _value

    override fun onSwitchCredential(credential: Credential) {
//        _value.reduce { it.copy(credential = credential) }
    }

    override fun onAddCredential(name: String, key: String, secret: String) {
        store.accept(Intent.Add(name, key, secret))
    }

    private fun createChild(config: Configuration, componentContext: ComponentContext): AppRoot.Child =
        when (config) {
            is Configuration.

            Ec2 -> AppRoot.Child.Ec2Content(ec2(componentContext))
        }
}

private sealed class Configuration : Parcelable {
    @Parcelize
    object Ec2 : Configuration()
}
