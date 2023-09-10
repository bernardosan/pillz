package eu.bernardosan.pillz.model

data class User(
    val name: String,
    val email: String,
    val pillsList: ArrayList<PillModel>
)
