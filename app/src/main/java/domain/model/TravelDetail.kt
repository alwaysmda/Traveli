package domain.model

sealed class TravelDetail(
    val viewType: Int
) {

    data class Cover(val title: String?, val cover: String) : TravelDetail(VIEW_TYPE_COVER)
    data class Description(val title: String?, val description: String) : TravelDetail(VIEW_TYPE_DESCRIPTION)
    data class Link(val title: String?, val url: String) : TravelDetail(VIEW_TYPE_LINK)
    data class Image(val title: String?, val imageUrl: String, val description: String?) : TravelDetail(VIEW_TYPE_IMAGE)
    data class Video(val title: String?, val video: String, val description: String?) : TravelDetail(VIEW_TYPE_VIDEO)
    data class BookMark(var isBookMark: Boolean) : TravelDetail(VIEW_TYPE_BOOKMARK)
    data class User(val user: UserPreview) : TravelDetail(VIEW_TYPE_USER)

    companion object {
        const val VIEW_TYPE_COVER = 0
        const val VIEW_TYPE_IMAGE = 1
        const val VIEW_TYPE_DESCRIPTION = 2
        const val VIEW_TYPE_LINK = 3
        const val VIEW_TYPE_VIDEO = 4
        const val VIEW_TYPE_BOOKMARK = 5
        const val VIEW_TYPE_USER = 6
    }
}




