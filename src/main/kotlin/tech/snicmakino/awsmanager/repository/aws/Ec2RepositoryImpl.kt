package tech.snicmakino.awsmanager.repository.aws

import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import aws.sdk.kotlin.services.ec2.Ec2Client
import aws.sdk.kotlin.services.ec2.model.DescribeInstancesRequest
import tech.snicmakino.awsmanager.domain.model.Ec2Instance
import tech.snicmakino.awsmanager.domain.model.Ec2Status

class Ec2RepositoryImpl {

    private var client: Ec2Client = Ec2Client {
        region = "ap-northeast-1"
        credentialsProvider = StaticCredentialsProvider {
            accessKeyId = ""
            secretAccessKey = ""
        }
    }

    suspend fun getInstanceList(): List<Ec2Instance>? {
        val describeInstancesResponse = client.describeInstances(DescribeInstancesRequest {})

        return describeInstancesResponse.reservations?.flatMap { it ->
            when (it.instances) {
                null -> emptyList()
                else -> it.instances!!.map { instance ->
                    Ec2Instance(
                        name = instance.tags?.find { tag -> tag.key == "Name" }?.value,
                        instanceId = instance.instanceId,
                        status = Ec2Status.fromCode(instance.state?.code),
                    )
                }
            }
        }
    }
}