package ui.template

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.xodus.traveli.R
import com.xodus.traveli.databinding.FragmentTemplateBindingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import ui.base.BaseFragment
import util.extension.snack

@AndroidEntryPoint
class TemplateBindingFragment : BaseFragment<FragmentTemplateBindingBinding, TemplateEvents, TemplateAction, TemplateBindingViewModel>(R.layout.fragment_template_binding) {
    //    private var _binding: FragmentTemplateBindingBinding? = null
    //    private val binding: FragmentTemplateBindingBinding get() = _binding!!
    //    private val viewModel: TemplateBindingViewModel by viewModels()
    //    private lateinit var baseActivity: BaseActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vm: TemplateBindingViewModel by viewModels()
        initialize(vm)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        //        _binding = DataBindingUtil.inflate<FragmentTemplateBindingBinding>(inflater, R.layout.fragment_template_binding, container, false).apply {
        //            lifecycleOwner = viewLifecycleOwner
        //            vm = viewModel
        //        }
        //        baseActivity = requireActivity() as BaseActivity
        init()
        observeEvents()
        setBackHandler()
        viewModel.action.onStart()
        return binding.root
    }

    private fun init() {
        with(binding) {
        }
    }

    private fun setBackHandler() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
         //       baseActivity.binding.baseNvNavigation.selectedItemId = R.id.navigation_one
            }
        })
    }
    //    override fun onDestroy() {
    //        super.onDestroy()
    //        _binding = null
    //    }
    private fun observeEvents() = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
        viewModel.event.collectLatest {
            when (it) {
                is TemplateEvents.Rebind -> {
                    binding.vm = viewModel
                }
                is TemplateEvents.Snack  -> {
                    snack(binding.root, it.message)
                }
            }
        }
    }
}