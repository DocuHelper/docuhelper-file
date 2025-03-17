package org.bmserver.docuhelperfile.file

import org.bmserver.docuhelperfile.core.File
import org.bmserver.docuhelperfile.core.FileManager
import org.bmserver.docuhelperfile.core.FileServiceApplication
import org.bmserver.docuhelperfile.core.response.UploadUrl
import org.bmserver.docuhelperfile.core.useCase.CreateUploadUrlUseCase
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.net.URL
import java.util.UUID

@Service
class FileService(
    private val fileManager: FileManager,
    private val fileDBManager: FileDBManager,
) : FileServiceApplication {
    override fun getFileUploadPreSignedUrl(file: CreateUploadUrlUseCase): Mono<UploadUrl> =
        fileDBManager
            .save(file.toFile())
            .map {
                val url: URL =
                    fileManager.getUploadPreSignedUrl(
                        path = it.uuid.toString(),
                        name = it.name,
                    )

                UploadUrl(
                    uuid = it.uuid!!,
                    url = url,
                )
            }

    override fun getFileDownloadPreSignedUrl(uuid: UUID): Mono<URL> =
        fileDBManager
            .findById(uuid)
            .map {
                fileManager.getDownloadPreSignedUrl(
                    path = it.uuid.toString(),
                    name = it.name,
                )
            }

    override fun getFileDownloadUrl(uuid: UUID): Mono<URL> =
        fileDBManager
            .findById(uuid)
            .map {
                fileManager.getDownloadUrl(
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
