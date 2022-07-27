package domain.model

data class Contact(
    val phone: ContactItem,
    val email: ContactItem,
    val twitter: ContactItem,
    val instagram: ContactItem,
    val website: ContactItem,
)
