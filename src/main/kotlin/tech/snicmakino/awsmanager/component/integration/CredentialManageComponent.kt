package tech.snicmakino.awsmanager.component.integration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import tech.snicmakino.awsmanager.component.CredentialManage
import tech.snicmakino.awsmanager.component.CredentialManage.Model
import tech.snicmakino.awsmanager.component.store.CredentialStore
import tech.snicmakino.awsmanager.component.store.CredentialStore.State
import tech.snicmakino.awsmanager.component.store.CredentialStoreProvider
import tech.snicmakino.awsmanager.domain.model.AwsCredential
import tech.snicmakino.awsmanager.domain.repository.ConfigurationRepository
import tech.snicmakino.awsmanager.utils.asValue

class CredentialManageComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    repository: ConfigurationRepository,
) : CredentialManage, ComponentContext by componentContext {

    private val _value = MutableValue(AwsCredential("", "", "", ""))
    override val selectedCredential: Value<AwsCredential> = _value

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

internal val stateToModel: (State) -> Model =
    {
        Model(
            credentials = it.credentials,
            isDone = it.isDone
        )
    }
