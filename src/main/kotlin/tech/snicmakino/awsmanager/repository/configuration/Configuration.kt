package tech.snicmakino.awsmanager.repository.configuration

import kotlinx.serialization.Serializable

@Serializable
data class Configuration(var credentials: List<Credential>)

@Serializable
data class Credential(
    val id: String,
    val name: String,
    val awsAccessKeyId: String,
    val awsSecretAccessKey: String
)