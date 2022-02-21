package tech.snicmakino.awsmanager.domain.repository

import com.badoo.reaktive.completable.Completable
import com.badoo.reaktive.observable.Observable
import tech.snicmakino.awsmanager.domain.model.AwsCredential

interface ConfigurationRepository {
    fun getCredentials(): Observable<List<AwsCredential>>
    fun addCredential(name: String, key: String, secret: String): Completable
}