package org.bmserver.docuhelperfile.file

import org.bmserver.docuhelperfile.core.File
import org.bmserver.docuhelperfile.core.FileRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.UUID

@Component
class FileDBManager(
    private val fileR2dbcRepository: FileR2dbcRepository,
) : FileRepository {
    override fun save(file: File): Mono<File> = fileR2dbcRepository.save(file)

    override fun findById(uuid: UUID): Mono<File> = fileR2dbcRepository.findById(uuid)

    override fun delete(uuid: UUID): Mono<Void> = fileR2dbcRepository.deleteById(uuid)

    override fun updateFileToUsed(uuid: UUID): Mono<File> =
        findById(uuid).flatMap {
            it.isUsed()
            fileR2dbcRepository.save(it)
        }

    override fun isExistFile(uuid: UUID): Mono<Boolean> = fileR2dbcRepository.existsById(uuid)
}
