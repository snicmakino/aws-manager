package tech.snicmakino.awsmanager.domain.model

class Ec2Instance(
    val name: String?,
    val instanceId: String?,
    val status: Ec2Status?,
) {
    override fun toString(): String {
        return "Ec2Instance(name=$name, instanceId=$instanceId, status=$status)"
    }
}