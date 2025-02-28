package org.bmserver.docuhelperfile.file

import org.bmserver.docuhelperfile.core.File
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface FileR2dbcRepository : R2dbcRepository<File, UUID>
