package domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Long,
    val name: String?,
    val avatar: String,
    val bio: String?,
    val cover: String,
    val country: Country,
    val city: String,
    val hometown: String,
    val contact: Contact,
) : Parcelable
