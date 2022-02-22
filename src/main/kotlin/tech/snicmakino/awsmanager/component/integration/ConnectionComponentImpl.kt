package tech.snicmakino.awsmanager.component.integration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import tech.snicmakino.awsmanager.component.ConnectionComponent
import tech.snicmakino.awsmanager.component.ConnectionComponent.Model
import tech.snicmakino.awsmanager.component.store.CredentialStore
import tech.snicmakino.awsmanager.component.store.CredentialStoreProvider
import tech.snicmakino.awsmanager.domain.model.AwsCredential
import tech.snicmakino.awsmanager.domain.repository.ConfigurationRepository
import tech.snicmakino.awsmanager.utils.asValue

class ConnectionComponentImpl internal constructor(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    repository: ConfigurationRepository,
    override val onCredentialSelect: (credential: AwsCredential) -> Unit
) : ConnectionComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        CredentialStoreProvider(
            storeFactory = storeFactory,
            configurationRepository = repository
        ).provide()
    }

    override val models: Value<Model> = store.asValue().map(stateToModel)

    override fun addCredential(name: String, key: String, secret: String) {
        store.accept(CredentialStore.Intent.Add(name, key, secret))
    }
}

internal val stateToModel: (CredentialStore.State) -> Model =
    {
        Model(
            credentials = it.credentials,
            isDone = it.isDone
        )
    }
