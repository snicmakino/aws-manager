package settings

import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

class AppSettings {
    init {
        val settingFile = Paths.get("${System.getenv("APPDATA")}\\snic\\AWSManager", "settings.json")

        if (!Files.exists(settingFile)) {
            Files.createDirectories(settingFile.parent)
            Files.createFile(settingFile)
        }
    }

    private lateinit var credentials: MutableList<Credential>

    fun createCredential(
        name: String,
        awsAccessKeyId: String,
        awsSecretAccessKey: String
    ): Credential {
        val uuid = UUID.randomUUID().toString()
        val new = Credential(
            uuid,
            name,
            awsAccessKeyId,
            awsSecretAccessKey
        )
        credentials.add(new)
        return new
    }

    fun updateCredential(credential: Credential) {
        credentials = credentials.map { if (it.id == credential.id) credential else it } as MutableList<Credential>
    }
}

data class Credential(
    val id: String,
    private val name: String,
    private val awsAccessKeyId: String,
    private val awsSecretAccessKey: String
)
