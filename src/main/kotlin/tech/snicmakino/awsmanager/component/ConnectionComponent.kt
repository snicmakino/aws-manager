package tech.snicmakino.awsmanager.component

import com.arkivanov.decompose.value.Value
import tech.snicmakino.awsmanager.domain.model.AwsCredential

interface ConnectionComponent {
    val models: Value<Model>
    val onCredentialSelect: (credential: AwsCredential) -> Unit
    fun addCredential(name: String, key: String, secret: String)

    data class Model(
        val credentials: List<AwsCredential>,
        val isDone: Boolean
    )
}
