package tech.snicmakino.awsmanager.domain.model

class AwsCredential(
    val id: String,
    val name: String,
    private val awsAccessKeyId: String,
    private val awsSecretAccessKey: String
)
