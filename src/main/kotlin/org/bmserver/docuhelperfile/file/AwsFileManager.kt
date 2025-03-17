package org.bmserver.docuhelperfile.file

import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import jakarta.annotation.PostConstruct
import org.bmserver.docuhelperfile.core.FileManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.net.URL

@Component
class AwsFileManager(
    private val s3Client: AmazonS3Client,
) : FileManager {
    @Value("\${s3.bucket}")
    lateinit var bucketName: String

    override fun getUploadPreSignedUrl(
        path: String,
        name: String,
    ): URL {
        val preUrlReq =
            GeneratePresignedUrlRequest(bucketName, "$path/$name")
                .withMethod(HttpMethod.PUT)

        return s3Client.generatePresignedUrl(preUrlReq)
    }


    override fun getDownloadPreSignedUrl(path: String, name: String): URL {
        val preUrlReq =
            GeneratePresignedUrlRequest(bucketName, "$path/$name")
                .withMethod(HttpMethod.GET)

        return s3Client.generatePresignedUrl(preUrlReq)
    }

    override fun getDownloadUrl(
        path: String,
        name: String,
    ): URL = s3Client.getUrl(bucketName, "$path/$name")

    @PostConstruct
    fun test() {
        val buckets = s3Client.listBuckets()
        buckets.forEach {
            println(it.name)
        }

        val preUrlReq =
            GeneratePresignedUrlRequest(bucketName, "/test")
                .withMethod(HttpMethod.PUT)

        val url = s3Client.generatePresignedUrl(preUrlReq)

        println("preUrlReq: $url")
    }
}
