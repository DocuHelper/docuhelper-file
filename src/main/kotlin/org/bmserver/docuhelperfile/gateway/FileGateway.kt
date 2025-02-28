package org.bmserver.docuhelperfile.gateway

import org.bmserver.docuhelperfile.core.File
import org.bmserver.docuhelperfile.core.FileServiceApplication
import org.bmserver.docuhelperfile.core.response.UploadUrl
import org.bmserver.docuhelperfile.core.useCase.CreateUploadUrlUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.net.URL
import java.util.UUID

@RestController
@RequestMapping("/file")
class FileGateway(
    private val fileServiceApplication: FileServiceApplication,
) {
    @PostMapping
    fun uploadUrl(
        @RequestBody file: Mono<CreateUploadUrlUseCase>,
    ): Mono<UploadUrl> =
        file.flatMap {
            fileServiceApplication.getFileUploadPreSignedUrl(it)
        }

    @GetMapping("/{uuid}")
    fun fileUrl(
        @PathVariable uuid: UUID,
    ): Mono<URL> = fileServiceApplication.getFileUrl(uuid)

    @PatchMapping("/{uuid}/is-used")
    fun updateFileToUsed(
        @PathVariable uuid: UUID,
    ): Mono<File> = fileServiceApplication.updateFileToUsed(uuid)
}
