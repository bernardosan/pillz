package eu.bernardosan.pillz.model

import java.util.*
import kotlin.collections.ArrayList

data class PillModel(
    val name: String,
    val quantity: Int,
    val periodicity: Int,
    val startingDate: Long?,        // Starting point for scheduling
    val finishingDate: Long?,      // Optional end date for scheduling
    val daysOfWeek: List<Int>?,    // Days of the week for weekly pills
    val timeOfDay: String?,
    val image: String = ""
)
