package domain.model

data class DataTransaction(
    val balance: Long,
    val transactionList: List<Transaction>,
    val balanceString: String,
)
