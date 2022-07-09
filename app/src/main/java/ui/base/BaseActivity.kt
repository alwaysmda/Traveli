package ui.base

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.xodus.templatefive.R
import com.xodus.templatefive.databinding.ActivityBaseBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BaseActivity : ThemeAwareActivity() {
    private var _binding: ActivityBaseBinding? = null
    val binding: ActivityBaseBinding
        get() = _binding!!
    private var barHeight: Int = 0

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.remove("android:support:fragments")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.baseNvNavigation.post { barHeight = binding.baseNvNavigation.height }
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.baseActivity_fragment) as NavHostFragment
        NavigationUI.setupWithNavController(binding.baseNvNavigation, navHostFragment.navController)
        setLoading(false)
        setNavigationTabs()


        binding.baseNvNavigation.setOnItemSelectedListener { item ->
            // In order to get the expected behavior, you have to call default Navigation method manually
            NavigationUI.onNavDestinationSelected(item, navHostFragment.navController)

            return@setOnItemSelectedListener true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //FIXME: uncommenting this line causes crash after changing language (or maybe also rotate)
        //        _binding = null
    }

    fun setNavigationTabs() {
        binding.baseNvNavigation.menu.getItem(0)?.title = ""
        binding.baseNvNavigation.menu.getItem(1)?.title = ""
    }

    fun setLoading(visible: Boolean) {
        if (_binding != null)
            binding.baseFlLoading.isVisible = visible
    }

    fun hideNavigationBar(hide: Boolean, duration: Long = 500, onFinish: () -> (Unit) = {}) {
        //        if (barHeight == 0) {
        //            binding.baseNvNavigation.post {
        //                barHeight = binding.baseNvNavigation.height
        //                hideNavigationBar(hide, 0, onFinish)
        //            }
        //        } else {
        //            val animator: ValueAnimator = if (hide) {
        //                ValueAnimator.ofInt(binding.baseNvNavigation.height, 0)
        //            } else {
        //                ValueAnimator.ofInt(binding.baseNvNavigation.height, barHeight)
        //            }
        //            animator.duration = duration
        //            animator.addUpdateListener {
        //                val params = binding.baseNvNavigation.layoutParams as LinearLayout.LayoutParams
        //                params.height = it.animatedValue as Int
        //                binding.baseNvNavigation.layoutParams = params
        //            }
        //            animator.start()
        //            animator.doOnEnd {
        //                onFinish()
        //            }
        //        }
    }
}