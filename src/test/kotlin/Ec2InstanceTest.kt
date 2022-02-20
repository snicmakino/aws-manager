import io.kotest.core.spec.style.FunSpec
import kotlinx.coroutines.runBlocking
import tech.snicmakino.awsmanager.repository.aws.Ec2RepositoryImpl

class Ec2InstanceTest : FunSpec({

    test("test") {
        runBlocking {
            val repository = Ec2RepositoryImpl()
            val instances = repository.getInstanceList()
            println(instances.toString())
        }
    }
})
