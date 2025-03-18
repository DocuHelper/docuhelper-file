package org.bmserver.core.document

import java.util.UUID

data class Document(
    val uuid: UUID,
    val name: String,
    val state: String,
    val file: UUID,
    val owner: UUID,
)