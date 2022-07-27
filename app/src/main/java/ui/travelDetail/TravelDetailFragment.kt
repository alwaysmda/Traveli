package ui.travelDetail


import adapter.TravelDetailAdapter
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import com.xodus.traveli.R
import com.xodus.traveli.databinding.FragmentTravelDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import ui.base.BaseFragment

@AndroidEntryPoint
class TravelDetailFragment : BaseFragment<FragmentTravelDetailBinding, TravelDetailEvents, TravelDetailAction, TravelDetailViewModel>(R.layout.fragment_travel_detail) {


    private lateinit var travelDetailAdapter: TravelDetailAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: TravelDetailViewModel by viewModels()
        initialize(viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        observeToEvents()
        viewModel.action.onStart()
    }

    private fun observeToEvents() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.event.collect {
                when (it) {
                    is TravelDetailEvents.TravelDetailError   -> binding.progress.isVisible = false
                    is TravelDetailEvents.TravelDetailLoading -> {
                        binding.progress.isVisible = true
                    }
                    is TravelDetailEvents.UpdateTravelDetail  -> {
                        binding.apply {
                            progress.isVisible = false
                            travelDetailAdapter.submitList(it.travelDetails)
                        }
                    }
                    is TravelDetailEvents.OpenUrl             -> startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it.url)))
                }
            }
        }
    }


    private fun setUpRecyclerView() {
        travelDetailAdapter = TravelDetailAdapter(baseActivity, viewModel.exoPlayer, onLinkClick = {
            viewModel.action.onLinkClick(it)
        }, onPlayerViewClick = { lastPlayedVideoIndex, position ->
            if (lastPlayedVideoIndex != -1 && lastPlayedVideoIndex != position) {
                val videoViewHolder = binding.rvTravelDetail.findViewHolderForAdapterPosition(lastPlayedVideoIndex) as TravelDetailAdapter.VideoViewHolder
                videoViewHolder.binding.apply {
                    playerView.player = null
                    viewModel.exoPlayer.setMediaItem(MediaItem.fromUri("https://persian5.cdn.asset.aparat.com/aparat-video/bdaea06a8c07f580a463d150bb9730af44790032-360p.mp4?wmsAuthSign=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6ImM1MDA0ZDM1ZDU4MGJjMzk5NDVmNjk2Y2IwMTQ5NmM2IiwiZXhwIjoxNjU4OTIwOTQ4LCJpc3MiOiJTYWJhIElkZWEgR1NJRyJ9.kPJww86JRZqRSrjri07ip61IWYQwwZO217zJ-kfaXks"))
                }
            }
            travelDetailAdapter.setLastPlayedVideoIndex(position)

        })
        binding.rvTravelDetail.adapter = travelDetailAdapter

    }

}