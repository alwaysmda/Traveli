package domain.model

data class UserPreview(
    val id: Long,
    val name: String,
    val avatar: String,
) {
    companion object {

        const val LOADING_ID = 0L
        const val FAILURE_ID = -1L

        fun getLoadingItem() = UserPreview(LOADING_ID, "", "")
        fun getFailureItem() = UserPreview(FAILURE_ID, "", "")

        fun List<UserPreview>.cloned() = ArrayList(this.map { it.copy() })
    }
}
