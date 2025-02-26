package dev.pango.ohmylife.apps.mscore.di

import dev.pango.ohmylife.apps.mscore.infraestructure.rabbitmq.RabbitMQProvider
import dev.pango.ohmylife.apps.mscore.trash.TaskPublisher
import org.koin.dsl.module


val deprecatedRabbitmqModule = module {
    single { TaskPublisher() }
    single { RabbitMQProvider() }
}
