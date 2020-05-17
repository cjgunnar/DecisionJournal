package com.gmail.cjgunnar13.decisionjournal

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Represents a decision the user is tracking
 */
@Entity
data class Decision(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var title: String = "",
    var date: Date = Date(),
    var fields: MutableList<String> = mutableListOf() // empty list of strings
)
