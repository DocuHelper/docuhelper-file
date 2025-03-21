package org.bmserver.docuhelperfile.file.eventListener

import org.bmserver.core.common.event.EventKey
import org.bmserver.core.document.event.DocumentCreate
import org.bmserver.docuhelperfile.common.AbstractEventListener
import org.bmserver.docuhelperfile.core.FileServiceApplication
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux

@Component
class DocumentCreateEventListener(
    private val reactiveKafkaConsumerTemplate: ReactiveKafkaConsumerTemplate<EventKey, Any>,
    private val fileServiceApplication: FileServiceApplication
) : AbstractEventListener<DocumentCreate>(reactiveKafkaConsumerTemplate) {
    override fun handle(event: DocumentCreate): Flux<Any> {
        return fileServiceApplication
            .updateFileToUsed(event.document.file)
            .doOnNext { println(it) }
            .map { it as Any }
            .toFlux()
    }
}