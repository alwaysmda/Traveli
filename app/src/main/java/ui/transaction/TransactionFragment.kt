package ui.transaction

import adapter.TransactionAdapter
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.xodus.traveli.R
import com.xodus.traveli.databinding.FragmentTransactionBinding
import dagger.hilt.android.AndroidEntryPoint
import ui.base.BaseFragment
import ui.base.ContentWrapper
import ui.dialog.ConfirmBottomSheet
import ui.dialog.EditBottomSheet
import util.Constant

@AndroidEntryPoint
class TransactionFragment : BaseFragment<FragmentTransactionBinding, TransactionEvents, TransactionAction, TransactionViewModel>(R.layout.fragment_transaction) {
    private val args: TransactionFragmentArgs by navArgs()
    private var editBottomSheet: EditBottomSheet? = null
    private var confirmBottomSheet: ConfirmBottomSheet? = null
    private var transactionAdapter: TransactionAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vm: TransactionViewModel by viewModels()
        initialize(vm)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeToEvents()
        super.onViewCreated(view, savedInstanceState)
        viewModel.action.onStart(args.balance)
        init()
    }

    private fun init() {
        binding.apply {
            binding.transactionCwTransaction.setStatus(ContentWrapper.WrapperStatus.Loading)
            transactionBtnCheckout.tag = View.VISIBLE
            transactionRvTransaction.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    transactionVShadow.alpha = if (recyclerView.computeVerticalScrollOffset() > 0) 0.2F else 0F
                    transactionBtnCheckout.apply {
                        if (dy > 0 && tag == View.VISIBLE) {
                            tag = View.GONE
                            this.animate().scaleX(0F).scaleY(0F).translationY(40F).alpha(0F).setDuration(100).withEndAction {
                                isVisible = false
                            }.start()
                        } else if (dy < 0 && tag == View.GONE) {
                            tag = View.VISIBLE
                            isVisible = true
                            this.animate().scaleX(1F).scaleY(1F).translationY(0F).alpha(1F).setDuration(100).start()
                        }
                    }
                    if (recyclerView.computeVerticalScrollExtent() +
                        recyclerView.computeVerticalScrollOffset() >
                        recyclerView.computeVerticalScrollRange() - Constant.CON_PAGINATE_OFFSET
                    ) {
                        viewModel.action.paginate()
                    }
                }
            })
        }
    }

    private fun observeToEvents() = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
        viewModel.event.collect {
            when (it) {
                is TransactionEvents.NavBack                   -> findNavController().popBackStack()
                is TransactionEvents.ShowLoading               -> showLoading(it.show)
                is TransactionEvents.Snack                     -> snack(it.message)
                is TransactionEvents.SetTransactionListFailure -> binding.transactionCwTransaction.setStatus(ContentWrapper.WrapperStatus.Failure(it.message))
                is TransactionEvents.UpdateTransactionList     -> {
                    binding.transactionCwTransaction.setStatus(ContentWrapper.WrapperStatus.Success)
                    if (binding.transactionRvTransaction.adapter == null) {
                        transactionAdapter = TransactionAdapter(baseActivity) {
                            viewModel.action.onRetryTransactionClick()
                        }
                        binding.transactionRvTransaction.adapter = transactionAdapter
                    }
                    transactionAdapter?.submitList(it.transactionList)
                }
                is TransactionEvents.SetTransactionListLoading -> binding.transactionCwTransaction.setStatus(ContentWrapper.WrapperStatus.Loading)
                is TransactionEvents.ShowDialog                -> confirmBottomSheet = ConfirmBottomSheet(
                    baseActivity,
                    it.icon,
                    it.iconColor,
                    it.title,
                    it.content,
                    it.positive,
                    it.onPositive,
                    it.negative,
                    it.onNegative
                ).also { d -> d.show(childFragmentManager, "") }
            }
        }
    }
}