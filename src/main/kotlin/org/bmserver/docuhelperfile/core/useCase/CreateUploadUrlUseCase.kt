package org.bmserver.docuhelperfile.core.useCase

import org.bmserver.docuhelperfile.core.File

class CreateUploadUrlUseCase(
    val name: String,
    val extension: String,
    val size: Long,
) {
    fun toFile() =
        File(
            name = name,
            size = size,
            extension = extension,
        )
}
