package domain.model

import kotlin.random.Random

data class UserPreview(
    val id: Long,
    val name: String,
    val avatar: String,
) {
    companion object {
        const val denisImage = "https://upload.wikimedia.org/wikipedia/commons/2/23/Dennis_Ritchie_2011.jpg"
        fun getFake() = UserPreview(Random.nextLong(), "Denis", denisImage)
        fun getFakes(): List<UserPreview> {
            val userPreviews = mutableListOf<UserPreview>()
            repeat(10) {
                userPreviews.add(getFake())
            }
            return userPreviews
        }
    }
}
