package ui.dialog

import adapter.ChargePriceAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.xodus.traveli.R
import com.xodus.traveli.databinding.SheetChargePriceBinding
import domain.model.ChargePrice
import domain.model.PriceItem.Companion.cloned
import ui.base.BaseActivity
import util.extension.separateNumberBy3
import util.extension.translate

class ChargeBottomSheet(
    private val baseActivity: BaseActivity,
    private val chargePrice: ChargePrice,
    private val onPositive: (Long) -> Unit
) : BottomSheetDialogFragment() {
    private var _binding: SheetChargePriceBinding? = null
    val binding: SheetChargePriceBinding get() = _binding!!
    private var adapter: ChargePriceAdapter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DataBindingUtil.inflate<SheetChargePriceBinding>(inflater, R.layout.sheet_charge_price, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR.app, baseActivity.app)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            adapter = ChargePriceAdapter(baseActivity) { index, item ->
                showError(null)
                chargePrice.priceList.forEach {
                    it.selected = it.value == item.value
                }
                adapter?.submitList(chargePrice.priceList.cloned())
                sheetChargeRbCustom.isChecked = false
                sheetChargeEtCustom.setText("")
            }
            sheetChargeRvPrice.adapter = adapter
            adapter?.submitList(chargePrice.priceList.cloned())
            sheetChargeRbCustom.isVisible = chargePrice.isCustom
            sheetChargeTlCustom.isVisible = chargePrice.isCustom
            sheetChargeEtCustom.doOnTextChanged { text, start, before, count ->
                showError(null)
                if (text.toString().isNotEmpty()) {
                    checkCustom()
                }
            }
            sheetChargeRbCustom.setOnClickListener {
                checkCustom(true)
            }
            sheetChargeBtnNegative.setOnClickListener {
                dismiss()
            }
            sheetChargeBtnPositive.setOnClickListener {
                val value = sheetChargeEtCustom.text.toString().toLongOrNull() ?: 0L
                if (sheetChargeRbCustom.isChecked) {
                    when {
                        value < chargePrice.min -> showError(baseActivity.app.m.priceCannotBeLowerThanX("$${chargePrice.min.separateNumberBy3().translate()}"))
                        value > chargePrice.max -> showError(baseActivity.app.m.priceCannotBeHigherThanX("$${chargePrice.max.separateNumberBy3().translate()}"))
                        else                    -> {
                            onPositive(value)
                            dismiss()
                        }
                    }
                } else {
                    chargePrice.priceList.firstOrNull { it.selected }?.value?.let {
                        onPositive(it)
                        dismiss()
                    }
                }
            }
        }
    }

    private fun checkCustom(force: Boolean = false) {
        if (binding.sheetChargeRbCustom.isChecked.not() || force) {
            showError(null)
            binding.sheetChargeRbCustom.isChecked = true
            chargePrice.priceList.forEach {
                it.selected = false
            }
            adapter?.submitList(chargePrice.priceList.cloned())
        }
    }

    fun showLoading(show: Boolean) {
        binding.sheetChargeFlLoading.isVisible = show
    }

    fun showError(error: String?) {
        showLoading(false)
        binding.sheetChargeTvError.text = error
    }
}