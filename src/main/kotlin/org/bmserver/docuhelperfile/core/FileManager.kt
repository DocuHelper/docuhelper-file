package org.bmserver.docuhelperfile.core

import java.net.URL

interface FileManager {
    fun getUploadPreSignedUrl(
        path: String,
        name: String,
    ): URL

    fun getDownloadPreSignedUrl(
        path: String,
        name: String,
    ): URL

    fun getDownloadUrl(
        path: String,
        name: String,
    ): URL
}
