package domain.model.travel

sealed class TravelDetail(
    val viewType: Int
) {

    data class Cover(val title: String, val cover: String) : TravelDetail(VIEW_TYPE_COVER)
    data class Description(val description: String) : TravelDetail(VIEW_TYPE_DESCRIPTION)
    data class Link(val url: String) : TravelDetail(VIEW_TYPE_LINK)
    data class Image(val imageUrl: String) : TravelDetail(VIEW_TYPE_IMAGE)
    data class Video(val video: String) : TravelDetail(VIEW_TYPE_VIDEO)
    object BookMark : TravelDetail(VIEW_TYPE_BOOKMARK)

    companion object {
        const val VIEW_TYPE_COVER = 0
        const val VIEW_TYPE_IMAGE = 1
        const val VIEW_TYPE_DESCRIPTION = 2
        const val VIEW_TYPE_LINK = 3
        const val VIEW_TYPE_VIDEO = 4
        const val VIEW_TYPE_BOOKMARK = 5
    }
}





