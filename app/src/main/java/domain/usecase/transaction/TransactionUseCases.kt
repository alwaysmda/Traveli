package domain.usecase.transaction

data class TransactionUseCases(
    val getTransactionListUseCase: GetTransactionListUseCase,
    val chargeUseCase: ChargeUseCase,
    val checkoutUseCase: CheckoutUseCase,
    val getBalanceUseCase: GetBalanceUseCase,
)
