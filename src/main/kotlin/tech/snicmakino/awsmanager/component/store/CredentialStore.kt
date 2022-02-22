package tech.snicmakino.awsmanager.component.store

import com.arkivanov.mvikotlin.core.store.Store
import tech.snicmakino.awsmanager.component.store.CredentialStore.Intent
import tech.snicmakino.awsmanager.component.store.CredentialStore.State
import tech.snicmakino.awsmanager.domain.model.AwsCredential

internal interface CredentialStore : Store<Intent, State, Nothing> {
    sealed class Intent {
        data class Add(
            val name: String,
            val key: String,
            val secret: String
        ) : Intent()

        data class Edit(
            val id: String,
            val name: String,
            val key: String,
            val secret: String
        ) : Intent()

        data class Delete(
            val id: String,
        ) : Intent()
    }

    data class State(
        val credentials: List<AwsCredential> = emptyList(),
        val isDone: Boolean = false
    )
}
