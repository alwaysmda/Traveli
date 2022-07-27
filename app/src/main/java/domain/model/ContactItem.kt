package domain.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import util.Constant

data class ContactItem(
    val title: String,
    val value: String?,
    val type: Constant.ContactType,
    @DrawableRes
    val icon: Int,
    @ColorRes
    val color: Int,
)
