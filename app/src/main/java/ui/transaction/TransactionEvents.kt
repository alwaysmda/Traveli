package ui.transaction

import domain.model.ChargePrice
import domain.model.Transaction
import ui.base.BaseEvent

sealed class TransactionEvents : BaseEvent() {
    //General
    class Snack(val message: String) : TransactionEvents()
    class ShowLoading(val show: Boolean) : TransactionEvents()
    class ShowDialog(
        val icon: Int?,
        val iconColor: Int?,
        val title: String,
        val content: String,
        val positive: String?,
        val onPositive: () -> Unit = {},
        val negative: String?,
        val onNegative: () -> Unit = {},
    ) : TransactionEvents()

    //Nav
    object NavBack : TransactionEvents()

    //
    class UpdateTransactionList(val transactionList: List<Transaction>) : TransactionEvents()
    class SetTransactionListFailure(val message: String) : TransactionEvents()
    object SetTransactionListLoading : TransactionEvents()
    class ShowChargeSheet(val chargePrice: ChargePrice) : TransactionEvents()
}
