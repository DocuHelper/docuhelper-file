package org.bmserver.docuhelperfile.common

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate
import reactor.core.publisher.Flux

abstract class AbstractEventListener<T>(
    private val kafkaConsumerTemplate: ReactiveKafkaConsumerTemplate<String, Any>
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        kafkaConsumerTemplate.receiveAutoAck()
            .flatMap {
                handle(it.value() as T)
            }
            .subscribe()
    }

    abstract fun handle(event: T): Flux<Any>

}