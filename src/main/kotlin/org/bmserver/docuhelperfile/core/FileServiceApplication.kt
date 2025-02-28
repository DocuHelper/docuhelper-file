package org.bmserver.docuhelperfile.core

import org.bmserver.docuhelperfile.core.response.UploadUrl
import org.bmserver.docuhelperfile.core.useCase.CreateUploadUrlUseCase
import reactor.core.publisher.Mono
import java.net.URL
import java.util.UUID

interface FileServiceApplication {
    fun getFileUploadPreSignedUrl(file: CreateUploadUrlUseCase): Mono<UploadUrl>

    fun getFileUrl(uuid: UUID): Mono<URL>

    fun isExistFile(uuid: UUID): Mono<Boolean>

    fun updateFileToUsed(uuid: UUID): Mono<File>
}
