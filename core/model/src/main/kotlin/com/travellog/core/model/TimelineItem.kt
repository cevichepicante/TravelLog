package com.travellog.core.model

import java.time.Instant

sealed interface TimelineItem {
    val timestamp: Instant

    data class PhotoItem(override val timestamp: Instant, val photo: Photo) : TimelineItem
    data class PlaceItem(override val timestamp: Instant, val place: Place) : TimelineItem
    data class MemoItem(override val timestamp: Instant, val memo: Memo) : TimelineItem
}
