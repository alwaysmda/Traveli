package domain.model

data class TravelPreview(
    val id: Int,
    val name: String,
    val image: String,
) {

    companion object {
        fun List<TravelPreview>.cloned() = ArrayList(this.map { it.copy() })
    }

}
