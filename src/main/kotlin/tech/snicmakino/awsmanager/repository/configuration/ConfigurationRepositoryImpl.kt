package tech.snicmakino.awsmanager.repository.configuration

import com.badoo.reaktive.completable.Completable
import com.badoo.reaktive.observable.asCompletable
import com.badoo.reaktive.observable.observable
import com.badoo.reaktive.observable.observeOn
import com.badoo.reaktive.observable.subscribeOn
import com.badoo.reaktive.scheduler.ioScheduler
import com.badoo.reaktive.scheduler.mainScheduler
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToStream
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
        return observable<Any> {
            val uuid = UUID.randomUUID().toString()

            val filestr = String(Files.readAllBytes(credentialFile))
            val configuration = if (filestr.isEmpty()) {
                Configuration(emptyList())
            } else {
                Json.decodeFromString<Configuration>(
                    String(Files.readAllBytes(credentialFile))
                )
            }
            configuration.credentials += Credential(
                uuid,
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
            .subscribeOn(ioScheduler)
            .observeOn(mainScheduler)
            .asCompletable()
    }

    fun add(name: String, key: String, secret: String) {
    }

//    fun updateCredential(credential: Credential) {
//        credentials = credentials.map { if (it.id == credential.id) credential else it } as MutableList<Credential>
//    }
}
