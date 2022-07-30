package domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    val phone: ContactItem,
    val email: ContactItem,
    val twitter: ContactItem,
    val instagram: ContactItem,
    val website: ContactItem,
) : Parcelable
