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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.xodus.traveli.R
import com.xodus.traveli.databinding.FragmentTravelDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import domain.model.TravelDetail
import ui.base.BaseFragment

@AndroidEntryPoint
class TravelDetailFragment : BaseFragment<FragmentTravelDetailBinding, TravelDetailEvents, TravelDetailAction, TravelDetailViewModel>(R.layout.fragment_travel_detail) {


    private lateinit var travelDetailAdapter: TravelDetailAdapter

    private val args: TravelDetailFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: TravelDetailViewModel by viewModels()
        initialize(viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        observeToEvents()
        viewModel.action.onStart(args.travelPreview)


    }

    private fun observeToEvents() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.event.collect {
                when (it) {
                    is TravelDetailEvents.TravelDetailError -> binding.progress.isVisible = false
                    is TravelDetailEvents.TravelDetailLoading -> {
                        binding.progress.isVisible = true
                    }
                    is TravelDetailEvents.UpdateTravelDetail -> {
                        binding.apply {
                            progress.isVisible = false
                            travelDetailAdapter.submitList(it.travelDetails)
                        }
                    }
                    is TravelDetailEvents.OpenUrl -> startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it.url)))
                    TravelDetailEvents.NavBack -> findNavController().popBackStack()
                }
            }
        }
    }


    private fun setUpRecyclerView() {
        travelDetailAdapter = TravelDetailAdapter(baseActivity, viewModel.exoPlayer, onLinkClick = {
            viewModel.action.onLinkClick(it)
        }, onPlayerViewClick = { lastPlayedVideoIndex, position ->


            binding.rvTravelDetail.findViewHolderForAdapterPosition(lastPlayedVideoIndex)?.let {
                if (lastPlayedVideoIndex != -1 && lastPlayedVideoIndex != position) {
                    val videoViewHolder = it as TravelDetailAdapter.VideoViewHolder
                    videoViewHolder.binding.apply {
                        playerView.player = null
                        ivVideoPreview.visibility = View.VISIBLE
                        ivPlay.visibility = View.VISIBLE
                    }
                }
            }
            viewModel.exoPlayer.setMediaItem(MediaItem.fromUri((travelDetailAdapter.currentList[position] as TravelDetail.Video).video))
            travelDetailAdapter.setLastPlayedVideoIndex(position)
        },
            onBackPress = { viewModel.action.onBackPress() },
            onBookMarkClick = {}
        )
        binding.rvTravelDetail.adapter = travelDetailAdapter

    }

}