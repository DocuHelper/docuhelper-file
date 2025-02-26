package org.bmserver.docuhelperfile.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class S3Config {
    @Value("\${s3.bucket}")
    lateinit var bucketName: String

    @Value("\${s3.endPoint}")
    lateinit var endPoint: String

    @Value("\${s3.region}")
    lateinit var region: String

    @Value("\${s3.credentials.accessKey}")
    lateinit var accessKey: String

    @Value("\${s3.credentials.secretKey}")
    lateinit var secretKey: String

    @Bean
    fun amazonS3Client(): AmazonS3Client {
        var awsCredentials: BasicAWSCredentials? = null
        val clientBuilder = AmazonS3ClientBuilder.standard()

        clientBuilder.setEndpointConfiguration(
            AwsClientBuilder.EndpointConfiguration(
                endPoint,
                region,
            ),
        )
        awsCredentials = BasicAWSCredentials(accessKey, secretKey)
        clientBuilder.enablePathStyleAccess()

        clientBuilder.withCredentials(AWSStaticCredentialsProvider(awsCredentials))

        return clientBuilder.build() as AmazonS3Client
    }

    @PostConstruct
    fun init() {
        println("bucketName: " + bucketName)
        println("accessKey: " + accessKey)
        println("secretKey: " + secretKey)
    }
}
