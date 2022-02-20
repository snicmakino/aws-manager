package tech.snicmakino.awsmanager.domain.repository

import com.badoo.reaktive.completable.Completable

interface ConfigurationRepository {
    abstract fun addCredential(name: String, key: String, secret: String): Completable
}