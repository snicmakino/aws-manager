package tech.snicmakino.awsmanager.component.integration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.push
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import tech.snicmakino.awsmanager.component.AppRootComponent
import tech.snicmakino.awsmanager.component.AppRootComponent.Child
import tech.snicmakino.awsmanager.component.ConnectionComponent
import tech.snicmakino.awsmanager.component.store.CredentialStoreProvider
import tech.snicmakino.awsmanager.domain.model.AwsCredential
import tech.snicmakino.awsmanager.domain.repository.ConfigurationRepository

class AppRootComponentImpl(
    private val componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
    private val repository: ConfigurationRepository
) : AppRootComponent, ComponentContext by componentContext {

    private val store =
        instanceKeeper.getStore {
            CredentialStoreProvider(
                storeFactory = storeFactory,
                configurationRepository = repository
            ).provide()
        }

    private val router = router<Config, Child>(
        initialConfiguration = Config.Connection,
        handleBackButton = false,
        childFactory = ::createChild
    )

    override val routerState: Value<RouterState<*, Child>> = router.state

    private fun createChild(config: Config, componentContext: ComponentContext): Child =
        when (config) {
            is Config.Connection -> Child.Connection(connection(componentContext))
            is Config.Ec2 -> Child.Ec2Content(ec2(componentContext, config))
        }

    private fun connection(componentContext: ComponentContext): ConnectionComponent =
        ConnectionComponentImpl(
            componentContext = componentContext,
            storeFactory = storeFactory,
            repository = repository,
            onCredentialSelect = { router.push(Config.Ec2(it)) }
        )

    private fun ec2(componentContext: ComponentContext, config: Config.Ec2) =
        Ec2ComponentImpl(
            componentContext = componentContext,
            credential = config.credential
        )

    private sealed class Config : Parcelable {
        @Parcelize
        object Connection : Config()

        @Parcelize
        data class Ec2(val credential: AwsCredential) : Config()
    }
}
