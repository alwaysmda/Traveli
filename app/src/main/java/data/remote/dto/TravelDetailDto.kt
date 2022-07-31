package data.remote.dto

import domain.model.TravelDetail.Companion.VIEW_TYPE_DESCRIPTION
import domain.model.TravelDetail.Companion.VIEW_TYPE_IMAGE
import domain.model.TravelDetail.Companion.VIEW_TYPE_LINK
import domain.model.TravelDetail.Companion.VIEW_TYPE_VIDEO

data class TravelDetailDto(
    val type: Int,
    val data: BaseData
) {

    companion object {
        fun getFakes() = mutableListOf(
            TravelDetailDto(VIEW_TYPE_IMAGE, ImageDto("", "https://www.konnecthq.com/wp-content/uploads/2019/07/Caribbean-Sea-31-12-1.jpg")),
            TravelDetailDto(VIEW_TYPE_DESCRIPTION, DescriptionDto("", "I went to carabian sea alone. there was shark there and that shark wanted to eat me but fortunately somehow I ran away")),
            TravelDetailDto(VIEW_TYPE_LINK, LinkDto("https://www.google.com")),
            TravelDetailDto(VIEW_TYPE_VIDEO, VideoDto("", "https://hajifirouz9.cdn.asset.aparat.com/aparat-video/18bed9b368a2983caf10ab51d106307940554504-240p.mp4?wmsAuthSign=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6IjFhYmFjMjA4NjNjYTBhZDE5NGRjZTJjN2IyYWZjMWFlIiwiZXhwIjoxNjU5MTgxNzk0LCJpc3MiOiJTYWJhIElkZWEgR1NJRyJ9.aHw2f3bTcq0MF0dBzSuqIJ3BHFW1BlQ_jZ049ToeliE", "https://png.pngtree.com/png-vector/20190828/ourmid/pngtree-flat-design-different-sea-views-png-image_1695410.jpg")),
            TravelDetailDto(VIEW_TYPE_VIDEO, VideoDto("", "https://hajifirouz9.cdn.asset.aparat.com/aparat-video/18bed9b368a2983caf10ab51d106307940554504-240p.mp4?wmsAuthSign=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6IjFhYmFjMjA4NjNjYTBhZDE5NGRjZTJjN2IyYWZjMWFlIiwiZXhwIjoxNjU5MTgxNzk0LCJpc3MiOiJTYWJhIElkZWEgR1NJRyJ9.aHw2f3bTcq0MF0dBzSuqIJ3BHFW1BlQ_jZ049ToeliE", "https://png.pngtree.com/png-vector/20190828/ourmid/pngtree-flat-design-different-sea-views-png-image_1695410.jpg")),
        )
    }

}


interface BaseData
data class CoverDto(val title: String, val image: String) : BaseData
data class ImageDto(val title: String, val image: String) : BaseData
data class DescriptionDto(val title: String, val description: String) : BaseData
data class LinkDto(val url: String) : BaseData
data class VideoDto(val title: String, val video: String, val frame: String) : BaseData
