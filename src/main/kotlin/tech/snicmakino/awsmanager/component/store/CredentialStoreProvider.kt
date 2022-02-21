package tech.snicmakino.awsmanager.component.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.reaktive.ReaktiveExecutor
import com.badoo.reaktive.observable.observeOn
import com.badoo.reaktive.scheduler.mainScheduler
import tech.snicmakino.awsmanager.component.store.CredentialStore.Intent
import tech.snicmakino.awsmanager.component.store.CredentialStore.State
import tech.snicmakino.awsmanager.domain.repository.ConfigurationRepository

internal class CredentialStoreProvider(
    private val storeFactory: StoreFactory,
    private val configurationRepository: ConfigurationRepository
) {

    fun provide(): CredentialStore =
        object : CredentialStore, Store<Intent, State, Nothing> by storeFactory.create(
            name = "CredentialStore",
            initialState = State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Result {
        data class Added(val isDone: Boolean) : Result()
    }

    private inner class ExecutorImpl : ReaktiveExecutor<Intent, Unit, State, Result, Nothing>() {

        override fun executeAction(action: Unit, getState: () -> State) {
            configurationRepository
                .getCredentials()
                .observeOn(mainScheduler)
                .subscribeScoped()
        }

        override fun executeIntent(intent: Intent, getState: () -> State): Unit =
            when (intent) {
                is Intent.Add -> add(intent.name, intent.key, intent.secret)
                is Intent.Delete -> TODO()
                is Intent.Edit -> TODO()
                is Intent.List -> TODO()
            }

        private fun add(name: String, key: String, secret: String) {
            configurationRepository
                .addCredential(name, key, secret)
                .subscribeScoped()
        }
    }

    private object ReducerImpl : Reducer<State, Result> {
        override fun State.reduce(msg: Result): State =
            when (msg) {
                is Result.Added -> copy(isDone = msg.isDone)
            }
    }
}
