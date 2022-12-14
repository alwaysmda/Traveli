package ui.transaction

import domain.model.Balance
import ui.base.BaseAction

interface TransactionAction : BaseAction {
    fun onStart(balance: Balance)
    fun onBackClick()
    fun onChargeClick()
    fun onChargeConfirmClick(value: Long)
    fun onCheckoutClick()
    fun paginate()
    fun onRetryTransactionClick()
}