package adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.media3.common.C
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.xodus.traveli.R
import com.xodus.traveli.databinding.*
import domain.model.TravelDetail
import domain.model.TravelDetail.Companion.VIEW_TYPE_BOOKMARK
import domain.model.TravelDetail.Companion.VIEW_TYPE_CITIES
import domain.model.TravelDetail.Companion.VIEW_TYPE_COVER
import domain.model.TravelDetail.Companion.VIEW_TYPE_DESCRIPTION
import domain.model.TravelDetail.Companion.VIEW_TYPE_IMAGE
import domain.model.TravelDetail.Companion.VIEW_TYPE_LINK
import domain.model.TravelDetail.Companion.VIEW_TYPE_TAG
import domain.model.TravelDetail.Companion.VIEW_TYPE_USER
import domain.model.TravelDetail.Companion.VIEW_TYPE_VIDEO
import ui.base.BaseActivity


class TravelDetailAdapter(private val activity: BaseActivity, private val exoPlayer: ExoPlayer, private val onLinkClick: (link: String) -> Unit, private val onPlayerViewClick: (lastPlayedVideoIndex: Int, position: Int) -> Unit) : ListAdapter<TravelDetail, RecyclerView.ViewHolder>(DiffCallback()) {

    private var lastPlayedVideoIndex = -1


    inner class CoverViewHolder(private val binding: RowTravelDetailCoverBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(travelDetail: TravelDetail.Cover) {
            binding.apply {
                app = activity.app
                data = travelDetail
                tvTitle.isVisible = travelDetail.title.isNullOrEmpty().not()
            }

        }
    }

    inner class ImageViewHolder(private val binding: RowTravelDetailImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(travelDetail: TravelDetail.Image) {
            binding.apply {
                data = travelDetail
                tvTitle.isVisible = travelDetail.title.isNullOrEmpty().not()

            }
        }
    }

    inner class DescriptionViewHolder(private val binding: RowTravelDetailDescriptionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(travelDetail: TravelDetail.Description) {
            binding.apply {
                app = activity.app
                data = travelDetail
                tvTitle.isVisible = travelDetail.title.isNullOrEmpty().not()
            }
        }
    }

    inner class LinkViewHolder(private val binding: RowTravelDetailLinkBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(travelDetail: TravelDetail.Link) {
            binding.apply {
                app = activity.app
                data = travelDetail
                root.setOnClickListener {
                    onLinkClick(travelDetail.url)
                }
            }
        }
    }

    inner class VideoViewHolder(val binding: RowTravelDetailVideoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(travelDetail: TravelDetail.Video) {
            binding.apply {
                tvTitle.isVisible = travelDetail.title.isNullOrEmpty().not()
                val playPauseBtn = playerView.findViewById<AppCompatImageButton>(androidx.media3.ui.R.id.exo_play_pause)
                val previewsBtn = playerView.findViewById<AppCompatImageButton>(androidx.media3.ui.R.id.exo_prev)
                val nextBtn = playerView.findViewById<AppCompatImageButton>(androidx.media3.ui.R.id.exo_next)
                previewsBtn.visibility = View.GONE
                nextBtn.visibility = View.GONE

                ivVideoPreview.setOnClickListener {
                    ivVideoPreview.visibility = View.GONE
                    ivPlay.visibility = View.GONE
                    onPlayerViewClick(lastPlayedVideoIndex, bindingAdapterPosition)
                    playerView.player = exoPlayer
                    dispatchPlay(exoPlayer)
                }

                playPauseBtn.setOnClickListener {
                    val state = exoPlayer.playbackState
                    if (state == Player.STATE_IDLE || state == Player.STATE_ENDED || exoPlayer.playWhenReady.not()) {

                        dispatchPlay(exoPlayer)
                    } else {
                        dispatchPause(exoPlayer)
                    }
                }
                Picasso.get().load(travelDetail.image).into(binding.ivVideoPreview)

            }


        }
    }

    inner class BookMarkViewHolder(private val binding: RowTravelDetailBookmarkBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(travelDetail: TravelDetail.BookMark) {

        }
    }

    inner class UserViewHolder(private val binding: RowTravelDetailUserBindingImpl) : RecyclerView.ViewHolder(binding.root) {
        fun bind(travelDetail: TravelDetail.User) {
            binding.apply {
                app = activity.app
                data = travelDetail.user
            }
        }
    }

    inner class CityViewHolder(private val binding: RowTravelDetailCityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(city: TravelDetail.City) {
            val adapter = CityAdapter(activity).apply {
                submitList(city.cityList)
            }
            binding.apply {
                app = activity.app
                rvCity.adapter = adapter
            }
        }
    }

    inner class TagViewHolder(private val binding: RowTravelDetailTagBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tag: TravelDetail.Tag) {
            binding.apply {
                val tagAdapter = SimpleAdapter(activity)
                tagAdapter.submitList(tag.tagList)
                rvTags.adapter = tagAdapter
                app = activity.app
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_COVER  -> {
                CoverViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.row_travel_detail_cover,
                        parent,
                        false
                    )
                )

            }
            VIEW_TYPE_IMAGE  -> {
                ImageViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.row_travel_detail_image,
                        parent,
                        false
                    )
                )

            }
            VIEW_TYPE_DESCRIPTION -> {
                DescriptionViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.row_travel_detail_description,
                        parent,
                        false
                    )
                )
            }
            VIEW_TYPE_LINK        -> {
                LinkViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.row_travel_detail_link,
                        parent,
                        false
                    )
                )
            }
            VIEW_TYPE_VIDEO       -> {
                VideoViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.row_travel_detail_video,
                        parent,
                        false
                    )
                )

            }
            VIEW_TYPE_BOOKMARK    -> {
                BookMarkViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.row_travel_detail_bookmark,
                        parent,
                        false
                    )
                )

            }

            VIEW_TYPE_USER   -> {
                UserViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.row_travel_detail_user,
                        parent,
                        false
                    )
                )
            }

            VIEW_TYPE_CITIES -> {
                CityViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.row_travel_detail_city,
                        parent,
                        false
                    )
                )
            }

            VIEW_TYPE_TAG    -> {
                TagViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.row_travel_detail_tag,
                        parent,
                        false
                    )
                )
            }

            else             -> ImageViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.row_travel_detail_image,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CoverViewHolder       -> holder.bind(currentList[position] as TravelDetail.Cover)
            is ImageViewHolder       -> holder.bind(currentList[position] as TravelDetail.Image)
            is DescriptionViewHolder -> holder.bind(currentList[position] as TravelDetail.Description)
            is LinkViewHolder        -> holder.bind(currentList[position] as TravelDetail.Link)
            is VideoViewHolder       -> holder.bind(currentList[position] as TravelDetail.Video)
            is BookMarkViewHolder    -> holder.bind(currentList[position] as TravelDetail.BookMark)
            is UserViewHolder        -> holder.bind(currentList[position] as TravelDetail.User)
            is CityViewHolder        -> holder.bind(currentList[position] as TravelDetail.City)
            is TagViewHolder         -> holder.bind(currentList[position] as TravelDetail.Tag)
        }
    }


    private class DiffCallback : DiffUtil.ItemCallback<TravelDetail>() {
        override fun areItemsTheSame(oldItem: TravelDetail, newItem: TravelDetail) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: TravelDetail, newItem: TravelDetail) =
            oldItem == newItem
    }


    private fun dispatchPlay(player: Player) {
        val state = player.playbackState
        if (state == Player.STATE_IDLE) {
            player.prepare()
        } else if (state == Player.STATE_ENDED) {
            player.seekTo(player.currentMediaItemIndex, C.TIME_UNSET)
        }
        player.play()
    }

    private fun dispatchPause(player: Player) {
        player.pause()
    }


    fun getLastPlayedVideoIndex() = lastPlayedVideoIndex

    fun setLastPlayedVideoIndex(lastPlayedVideoIndex: Int) {
        this.lastPlayedVideoIndex = lastPlayedVideoIndex
    }


}

//
//private void dispatchPlayPause(Player player) {
//    @State int state = player.getPlaybackState();
//    if (state == Player.STATE_IDLE || state == Player.STATE_ENDED || !player.getPlayWhenReady()) {
//        dispatchPlay(player);
//    } else {
//        dispatchPause(player);
//    }
//}
//
//private void dispatchPlay(Player player) {
//    @State int state = player.getPlaybackState();
//    if (state == Player.STATE_IDLE) {
//        player.prepare();
//    } else if (state == Player.STATE_ENDED) {
//        seekTo(player, player.getCurrentMediaItemIndex(), C.TIME_UNSET);
//    }
//    player.play();
//}