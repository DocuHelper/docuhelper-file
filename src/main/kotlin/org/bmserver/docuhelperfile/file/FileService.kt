package org.bmserver.docuhelperfile.file

import org.bmserver.docuhelperfile.core.File
import org.bmserver.docuhelperfile.core.FileManager
import org.bmserver.docuhelperfile.core.FileServiceApplication
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.net.URL
import java.util.UUID

@Service
class FileService(
    private val fileManager: FileManager,
    private val fileDBManager: FileDBManager,
) : FileServiceApplication {
    override fun getFileUploadPreSignedUrl(file: File): Mono<URL> =
        fileDBManager
            .save(file)
            .map {
                fileManager.getPreSignedUrl(
                    path = it.uuid.toString(),
                    name = it.name,
                )
            }

    override fun getFileUrl(uuid: UUID): Mono<URL> =
        fileDBManager
            .findById(uuid)
            .map {
                fileManager.getUrl(
                    path = it.uuid.toString(),
                    name = it.name,
                )
            }

    override fun isExistFile(uuid: UUID): Mono<Boolean> = fileDBManager.isExistFile(uuid)

    override fun updateFileToUsed(uuid: UUID): Mono<File> =
        fileDBManager
            .findById(uuid)
            .flatMap {
                it.isUsed()
                fileDBManager.save(it)
            }
}
