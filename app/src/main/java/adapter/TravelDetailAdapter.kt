package adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xodus.templatefive.R
import com.xodus.templatefive.databinding.*
import domain.model.TravelDetail
import domain.model.TravelDetail.Companion.VIEW_TYPE_BOOKMARK
import domain.model.TravelDetail.Companion.VIEW_TYPE_COVER
import domain.model.TravelDetail.Companion.VIEW_TYPE_DESCRIPTION
import domain.model.TravelDetail.Companion.VIEW_TYPE_IMAGE
import domain.model.TravelDetail.Companion.VIEW_TYPE_LINK
import domain.model.TravelDetail.Companion.VIEW_TYPE_VIDEO
import ui.base.BaseActivity


class TravelDetailAdapter(private val activity: BaseActivity, private val exoPlayer: ExoPlayer, private val onLinkClick: (link: String) -> Unit, private val onVideoFullScreenClick: () -> Unit) : ListAdapter<TravelDetail, RecyclerView.ViewHolder>(DiffCallback()) {


    inner class CoverViewHolder(private val binding: RowTravelDetailCoverBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(travelDetail: TravelDetail.Cover) {
            binding.apply {
                app = activity.app
                data = travelDetail
            }

        }
    }

    inner class ImageViewHolder(private val binding: RowTravelDetailImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(travelDetail: TravelDetail.Image) {
            binding.apply {
                data = travelDetail
            }
        }
    }

    inner class DescriptionViewHolder(private val binding: RowTravelDetailDescriptionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(travelDetail: TravelDetail.Description) {
            binding.apply {
                app = activity.app
                data = travelDetail
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

    inner class VideoViewHolder(private val binding: RowTravelDetailVideoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(travelDetail: TravelDetail.Video) {
            binding.apply {
                playerView.player = exoPlayer
                playerView.setFullscreenButtonClickListener {}


            }


        }
    }

    inner class BookMarkViewHolder(private val binding: RowTravelDetailBookmarkBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(travelDetail: TravelDetail.BookMark) {

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_COVER       -> {
                CoverViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.row_travel_detail_cover,
                        parent,
                        false
                    )
                )

            }
            VIEW_TYPE_IMAGE       -> {
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
            else                  -> ImageViewHolder(
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
        }
    }


    private class DiffCallback : DiffUtil.ItemCallback<TravelDetail>() {
        override fun areItemsTheSame(oldItem: TravelDetail, newItem: TravelDetail) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: TravelDetail, newItem: TravelDetail) =
            oldItem == newItem
    }

}