package tech.snicmakino.awsmanager.component.integration

import com.arkivanov.decompose.ComponentContext
import tech.snicmakino.awsmanager.component.Ec2Component
import tech.snicmakino.awsmanager.domain.model.AwsCredential

class Ec2ComponentImpl internal constructor(
    componentContext: ComponentContext,
    credential: AwsCredential
) : Ec2Component, ComponentContext by componentContext {
}