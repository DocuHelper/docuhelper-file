package org.bmserver.docuhelperfile.file.eventListener

import org.bmserver.core.common.domain.event.EventHandler
import org.bmserver.core.document.event.DocumentCreate
import org.bmserver.docuhelperfile.core.FileServiceApplication
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class DocumentCreateEventListener(
    private val fileServiceApplication: FileServiceApplication
) : EventHandler<DocumentCreate> {

    override fun handle(event: DocumentCreate): Mono<Void> {
        return fileServiceApplication
            .updateFileToUsed(event.document.file)
            .doOnNext { println(it) }
            .map { it as Any }
            .then()
    }
}