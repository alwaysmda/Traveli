package domain.model

data class Transaction(
    val id: Long,
    val title: String,
    val amount: Long,
    val isPositive: Boolean,
    val isCheckedOut: Boolean,
    val amountString: String,
)