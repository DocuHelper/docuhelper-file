package org.bmserver.core.common.domain.event

import java.time.LocalDateTime

class EventKey(
    val eventType: String,
    val eventPublishDt: LocalDateTime
)