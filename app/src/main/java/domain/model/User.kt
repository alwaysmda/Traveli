package domain.model

data class User(
    val id: Long,
    val name: String,
    val avatar: String,
    val bio: String,
    val cover: String,
    val hometown: String,
    val contact: Contact,
)
