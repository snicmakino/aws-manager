package tech.snicmakino.awsmanager.component

import com.arkivanov.decompose.value.Value
import tech.snicmakino.awsmanager.domain.model.AwsCredential

interface CredentialManage {
    val models: Value<Model>
    val selectedCredential: Value<AwsCredential>
    fun addCredential(name: String, key: String, secret: String)

    data class Model(
        val credentials: List<AwsCredential>,
        val isDone: Boolean
    )
}
