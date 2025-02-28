package org.bmserver.docuhelperfile.core

import reactor.core.publisher.Mono
import java.util.UUID

interface FileRepository {
    fun save(file: File): Mono<File>

    fun findById(uuid: UUID): Mono<File>

    fun delete(uuid: UUID): Mono<Void>

    fun updateFileToUsed(uuid: UUID): Mono<File>

    fun isExistFile(uuid: UUID): Mono<Boolean>
}
