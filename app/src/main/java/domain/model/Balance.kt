package domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Balance(
    val balance: Long,
    val balanceString: String,
) : Parcelable
