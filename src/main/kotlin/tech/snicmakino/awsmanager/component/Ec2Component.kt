package tech.snicmakino.awsmanager.component

import com.arkivanov.decompose.ComponentContext

class Ec2Component internal constructor(
    componentContext: ComponentContext
) : Ec2, ComponentContext by componentContext {
}