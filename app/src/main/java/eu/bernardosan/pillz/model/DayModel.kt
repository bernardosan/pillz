package eu.bernardosan.pillz.model

data class DayModel(
    val dayOfMonth: Int,
    val dayOfWeek: String,
    val month: Int,
    val year: Int,
    val pills: ArrayList<ItemPill>
)
