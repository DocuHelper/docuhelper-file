package org.bmserver.docuhelperfile.config

import org.bmserver.core.common.domain.event.EventKey
import org.bmserver.core.common.domain.event.config.EventListener
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux


@Component
class KafkaEventListener(
    private val reactiveKafkaConsumerTemplate: ReactiveKafkaConsumerTemplate<EventKey, LinkedHashMap<String, String>>
) : EventListener {

    override fun consumeEvent(): Flux<Pair<EventKey, LinkedHashMap<String, String>>> = reactiveKafkaConsumerTemplate
        .receiveAutoAck()
        .map { Pair(it.key(), it.value()) }
}


