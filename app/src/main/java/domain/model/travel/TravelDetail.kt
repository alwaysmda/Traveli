package domain.model.travel

import data.remote.dto.GOOGLE_URL
import domain.model.City

data class TravelDetail(
    val name: String = "",
    val cover: String = "",
    val countryName: String = "",
    val description: String = "",
    val link: String = "",
    val cities: List<City> = listOf(),
    val image: String = "",
    val video: String = "",
    val viewType: Int = 0
) {
    companion object {
        val caribbean = "https://www.konnecthq.com/caribbean-sea/"
        fun getFake(): TravelDetail {
            return TravelDetail(
                "Amazing travel to barcelona", caribbean, "Spain", "I went to carabian sea alone.\n" +
                        "there was shark there and that shark wanted to eat me but fortunately somehow I ran away", GOOGLE_URL, City.getFakes(), caribbean, " https ://persian5.cdn.asset.aparat.com/aparat-video/bdaea06a8c07f580a463d150bb9730af44790032-240p.mp4?wmsAuthSign=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6ImQxNWVmMmM1NDgxMGQzMDIzYTkzZDZlM2IwNzIyOGNlIiwiZXhwIjoxNjU4MjQ4NjEzLCJpc3MiOiJTYWJhIElkZWEgR1NJRyJ9.Y121L3MRjZjjxywwgAulf1gaeaClRRB0fs4LPoh1NA0"
            )
        }

        fun getFakes(): List<TravelDetail> {
            return listOf(
                TravelDetail(countryName = "Brazil", cover = caribbean, viewType = VIEW_TYPE_COVER),
                TravelDetail(image = caribbean, viewType = VIEW_TYPE_IMAGE),
                TravelDetail(description = "there was shark there and that shark wanted to eat me but fortunately somehow I ran away", viewType = VIEW_TYPE_DESCRIPTION),
                TravelDetail(video = "https ://persian5.cdn.asset.aparat.com/aparat-video/bdaea06a8c07f580a463d150bb9730af44790032-240p.mp4?wmsAuthSign=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6ImQxNWVmMmM1NDgxMGQzMDIzYTkzZDZlM2IwNzIyOGNlIiwiZXhwIjoxNjU4MjQ4NjEzLCJpc3MiOiJTYWJhIElkZWEgR1NJRyJ9.Y121L3MRjZjjxywwgAulf1gaeaClRRB0fs4LPoh1NA0", viewType = VIEW_TYPE_VIDEO),
                TravelDetail(viewType = VIEW_TYPE_BOOKMARK)
            )

        }

        const val VIEW_TYPE_COVER = 0
        const val VIEW_TYPE_IMAGE = 1
        const val VIEW_TYPE_DESCRIPTION = 2
        const val VIEW_TYPE_LINK = 3
        const val VIEW_TYPE_VIDEO = 4
        const val VIEW_TYPE_BOOKMARK = 5

    }

}
