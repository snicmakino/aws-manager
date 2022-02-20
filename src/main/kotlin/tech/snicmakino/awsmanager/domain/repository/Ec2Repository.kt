package tech.snicmakino.awsmanager.domain.repository

import tech.snicmakino.awsmanager.domain.model.Ec2Instance

interface Ec2Repository {
    suspend fun getInstanceList(): List<Ec2Instance>?
}