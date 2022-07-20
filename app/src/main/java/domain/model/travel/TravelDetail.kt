package domain.model.travel

import data.remote.dto.GOOGLE_URL
import domain.model.City

data class TravelDetail(
    val name: String,
    val image: String,
    val countryName: String,
    val description: String,
    val link: String,
    val cities: List<City>,
    val images: String,
    val video: String,
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

        const val VIEW_TYPE_COVER = 0
        const val VIEW_TYPE_IMAGE = 1
        const val VIEW_TYPE_DESCRIPTION = 2
        const val VIEW_TYPE_LINK = 3
        const val VIEW_TYPE_VIDEO = 4
        const val VIEW_TYPE_BOOKMARK = 5

    }

}
