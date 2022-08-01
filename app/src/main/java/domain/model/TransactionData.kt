package domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionData(
    val hasMore: Boolean,
    val transactionList: List<Transaction>,
) : Parcelable
