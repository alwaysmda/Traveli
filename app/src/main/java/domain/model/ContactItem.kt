package domain.model

import android.os.Parcelable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize
import util.Constant

@Parcelize
data class ContactItem(
    val title: String,
    val value: String?,
    val type: Constant.ContactType,
    @DrawableRes
    val icon: Int,
    @ColorRes
    val color: Int,
) : Parcelable
