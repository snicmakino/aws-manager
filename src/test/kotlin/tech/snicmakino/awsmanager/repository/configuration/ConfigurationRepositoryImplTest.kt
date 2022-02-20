package tech.snicmakino.awsmanager.repository.configuration

import com.badoo.reaktive.test.completable.TestCompletable
import com.badoo.reaktive.test.completable.test
import com.badoo.reaktive.test.observable.TestObservable
import com.badoo.reaktive.utils.reaktiveUncaughtErrorHandler
import io.kotest.core.spec.style.FunSpec

class ConfigurationRepositoryImplTest : FunSpec({

    test("test") {
        val repository = ConfigurationRepositoryImpl
        TestCompletable()
        val add = repository.add("name", "key", "secret")
    }
})
