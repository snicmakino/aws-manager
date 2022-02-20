package tech.snicmakino.awsmanager.repository.configuration

import kotlinx.serialization.Serializable

@Serializable
data class Configuration(var credentials: List<Credential>)

@Serializable
data class Credential(
    val id: String,
    private val name: String,
    private val awsAccessKeyId: String,
    private val awsSecretAccessKey: String
)