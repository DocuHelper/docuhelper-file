package org.bmserver.docuhelperfile.common

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate
import reactor.core.publisher.Flux
import java.lang.reflect.ParameterizedType

abstract class AbstractEventListener<T>(
    private val kafkaConsumerTemplate: ReactiveKafkaConsumerTemplate<String, Any>
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        kafkaConsumerTemplate.receiveAutoAck()
            .filter { it.key() == getEventKey()}
            .flatMap {
                handle(it.value() as T)
            }
            .subscribe()
    }

    abstract fun handle(event: T): Flux<Any>

    private fun getEventKey(): String =  (this::class.java.genericSuperclass as ParameterizedType)
        .actualTypeArguments
        .first()
        .typeName
        .split(".")
        .last()
}