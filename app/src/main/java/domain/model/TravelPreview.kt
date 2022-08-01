package domain.model

data class TravelPreview(
    val id: Long,
    val name: String,
    val image: String,
) {
companion object {
    const val ADD_ID = 0L
    fun getAddItem() = TravelPreview(ADD_ID, "", "")
    fun List<TravelPreview>.cloned() = ArrayList(this.map { it.copy() })
}


}
