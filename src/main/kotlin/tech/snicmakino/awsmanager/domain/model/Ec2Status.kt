package tech.snicmakino.awsmanager.domain.model

enum class Ec2Status(val code: Int, val value: String) {
    PENDING(0, "pending"),
    RUNNING(16, "running"),
    SHUTTING_DOWN(32, "shutting down"),
    TERMINATED(48, "terminated"),
    STOPPING(64, "stopping"),
    STOPPED(80, "stopped");

    override fun toString(): String {
        return this.value
    }

    companion object {
        fun fromCode(code: Int?): Ec2Status? {
            return values().find { it.code == code }
        }
    }
}