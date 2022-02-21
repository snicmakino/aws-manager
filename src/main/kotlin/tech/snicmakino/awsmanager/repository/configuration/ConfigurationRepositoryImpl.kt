package tech.snicmakino.awsmanager.repository.configuration

import com.badoo.reaktive.completable.Completable
import com.badoo.reaktive.completable.completableFromFunction
import com.badoo.reaktive.observable.Observable
import com.badoo.reaktive.observable.observable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToStream
import tech.snicmakino.awsmanager.domain.model.AwsCredential
import tech.snicmakino.awsmanager.domain.repository.ConfigurationRepository
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption.TRUNCATE_EXISTING
import java.util.*

object ConfigurationRepositoryImpl : ConfigurationRepository {
    private val credentialFile: Path

    init {
        val credentialFile = Paths.get("${System.getenv("APPDATA")}/snic/AWSManager", "settings.json")

        if (!Files.exists(credentialFile)) {
            Files.createDirectories(credentialFile.parent)
            Files.createFile(credentialFile)
        }
        this.credentialFile = credentialFile
    }

    override fun addCredential(name: String, key: String, secret: String): Completable {
        return completableFromFunction {
            val filestr = String(Files.readAllBytes(credentialFile))
            val configuration = if (filestr.isEmpty()) {
                Configuration(emptyList())
            } else {
                Json.decodeFromString(
                    String(Files.readAllBytes(credentialFile))
                )
            }
            configuration.credentials += Credential(
                UUID.randomUUID().toString(),
                name,
                key,
                secret
            )
            val format = Json { prettyPrint = true }
            format.encodeToStream(
                configuration,
                Files.newOutputStream(credentialFile, TRUNCATE_EXISTING)
            )
        }
    }

    override fun getCredentials(): Observable<List<AwsCredential>> {
        return observable {
            val filestr = String(Files.readAllBytes(credentialFile))
            val configuration = if (filestr.isEmpty()) {
                Configuration(emptyList())
            } else {
                Json.decodeFromString(
                    String(Files.readAllBytes(credentialFile))
                )
            }
            it.onNext(
                configuration.credentials.map { credential ->
                    AwsCredential(
                        credential.id,
                        credential.name,
                        credential.awsAccessKeyId,
                        credential.awsSecretAccessKey
                    )
                }
            )
        }
    }
}
