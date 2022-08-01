package ui.transaction

import androidx.lifecycle.viewModelScope
import com.xodus.traveli.R
import dagger.hilt.android.lifecycle.HiltViewModel
import data.remote.DataState
import domain.model.Balance
import domain.model.Transaction
import domain.model.Transaction.Companion.cloned
import domain.model.Transaction.Companion.getFailureItem
import domain.model.Transaction.Companion.getLoadingItem
import domain.usecase.transaction.TransactionUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import main.ApplicationClass
import ui.base.BaseViewModel
import javax.inject.Inject

var transactionResponseCounter = 1 //todo remove

@HiltViewModel
class TransactionViewModel @Inject constructor(
    app: ApplicationClass,
    private val transactionUseCases: TransactionUseCases,
) : BaseViewModel<TransactionEvents, TransactionAction>(app), TransactionAction {
    //Bind
    val balanceText = MutableStateFlow("")
    val checkoutVisibility = MutableStateFlow(false)

    //Local
    private lateinit var balance: Balance
    private val transactionList = arrayListOf<Transaction>()
    private var isPaginating = false
    private var page = 1
    private var hasMore = true
    private var hasError = false

    override fun onStart(balance: Balance) {
        updateBalance(balance)
        paginate()
    }

    override fun onBackClick() {
        viewModelScope.launch {
            _event.send(TransactionEvents.NavBack)
        }
    }

    override fun onChargeClick() {
        viewModelScope.launch {
            _event.send(TransactionEvents.Snack(app.m.add))
        }
    }

    override fun onCheckoutClick() {
        viewModelScope.launch {
            _event.send(TransactionEvents.ShowDialog(R.drawable.ic_money, R.color.md_amber_300, app.m.confirmCheckout, app.m.confirmCheckoutDesc(balance.balanceString), app.m.checkout, {
                transactionUseCases.checkoutUseCase().onEach {
                    when (it) {
                        is DataState.Loading -> _event.send(TransactionEvents.ShowLoading(true))
                        is DataState.Failure -> {
                            _event.send(TransactionEvents.ShowLoading(false))
                            _event.send(TransactionEvents.Snack(it.message))
                        }
                        is DataState.Success -> {
                            _event.send(TransactionEvents.ShowLoading(false))
                            _event.send(TransactionEvents.Snack(app.m.checkoutComplete))
                            _event.send(TransactionEvents.SetTransactionListLoading)
                            updateBalance(balance)
                            transactionList.clear()
                            page = 1
                            isPaginating = false
                            hasError = false
                            hasMore = true
                            paginate()
                        }
                    }
                }.launchIn(viewModelScope)
            }, app.m.cancel))
        }
    }

    override fun onRetryTransactionClick() {
        viewModelScope.launch {
            if (transactionList.isEmpty()) {
                _event.send(TransactionEvents.SetTransactionListLoading)
            } else {
                val list = transactionList.cloned()
                list.add(getLoadingItem())
                _event.send(TransactionEvents.UpdateTransactionList(list))
            }
            hasError = false
            paginate()
        }
    }

    override fun paginate() {
        if (isPaginating.not() && hasMore && hasError.not()) {
            isPaginating = true
            transactionUseCases.getTransactionListUseCase(page).onEach {
                when (it) {
                    is DataState.Loading -> {
                        if (transactionList.isEmpty()) {
                            _event.send(TransactionEvents.SetTransactionListLoading)
                        }
                    }
                    is DataState.Failure -> {
                        hasError = true
                        isPaginating = false
                        if (transactionList.isEmpty()) {
                            _event.send(TransactionEvents.SetTransactionListFailure(it.message))
                        } else {
                            val list = transactionList.cloned()
                            list.add(getFailureItem())
                            _event.send(TransactionEvents.UpdateTransactionList(list))
                            _event.send(TransactionEvents.Snack(it.message))
                        }
                    }
                    is DataState.Success -> {
                        //hasMore = it.data.hasMore //todo uncomment
                        hasMore = transactionResponseCounter < 8 //todo remove
                        transactionList.addAll(it.data.transactionList)
                        val list = transactionList.cloned()
                        if (hasMore) {
                            list.add(getLoadingItem())
                        }
                        _event.send(TransactionEvents.UpdateTransactionList(list))
                        isPaginating = false
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun updateBalance(b: Balance) {
        balance = b
        checkoutVisibility.value = balance.balance > 0
        balanceText.value = balance.balanceString
    }
}