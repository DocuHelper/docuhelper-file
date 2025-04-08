package org.bmserver.core.document.event

import org.bmserver.core.common.domain.event.AbstractEvent
import org.bmserver.core.document.Document

data class DocumentCreate(val document: Document):AbstractEvent()

