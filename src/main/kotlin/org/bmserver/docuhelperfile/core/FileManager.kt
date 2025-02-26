package org.bmserver.docuhelperfile.core

import java.net.URL

interface FileManager {
    fun getPreSignedUrl(
        path: String,
        name: String,
    ): URL

    fun getUrl(
        path: String,
        name: String,
    ): URL
}
