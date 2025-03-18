package org.bmserver.docuhelperfile.config

import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate
import reactor.kafka.receiver.ReceiverOptions

@Configuration
class KafkaConfig {

    @Bean
    fun reactiveKafkaConsumerTemplate(props: KafkaProperties): ReactiveKafkaConsumerTemplate<String, Any> {
        val consumerProperties = props.buildConsumerProperties()
        consumerProperties.putAll(
            mapOf(
                "group.id" to "docuhelper-file",
                "spring.json.trusted.packages" to "*"
            )
        )

        val opt = ReceiverOptions.create<String, Any>(consumerProperties)
            .subscription(listOf("docuhelper-api"))

        return ReactiveKafkaConsumerTemplate(opt)
    }
}
