package dev.pango.ohmylife.apps.mscore.infraestructure.rabbitmq

import org.koin.dsl.module


val rabbitMQModule = module {
    single { RabbitMQProvider() }
    single { IaClient(get<RabbitMQProvider>().obtainChannel())}
}
