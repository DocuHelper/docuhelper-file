package org.bmserver.docuhelperfile.config

import org.bmserver.core.common.domain.event.EventKey
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate
import reactor.kafka.receiver.ReceiverOptions

@Configuration
class KafkaConfig {

    @Bean
    fun reactiveKafkaConsumerTemplate(props: KafkaProperties): ReactiveKafkaConsumerTemplate<EventKey, LinkedHashMap<String,String>> {
        val consumerProperties = props.buildConsumerProperties()
        consumerProperties.putAll(
            mapOf(
                "group.id" to "docuhelper-file",
                "spring.json.trusted.packages" to "*",
                "spring.json.use.type.headers" to false,
                "spring.json.key.default.type" to "org.bmserver.core.common.domain.event.EventKey",
                "spring.json.value.default.type" to "java.util.LinkedHashMap",
                "key.deserializer" to "org.springframework.kafka.support.serializer.JsonDeserializer",
                "value.deserializer" to "org.springframework.kafka.support.serializer.JsonDeserializer",
            )
        )

        val opt = ReceiverOptions.create<EventKey, LinkedHashMap<String, String>>(consumerProperties)
            .subscription(
                listOf(
                    "docuhelper-api",
                )
            )

        return ReactiveKafkaConsumerTemplate(opt)
    }
}
