package domain.model.travel

import domain.model.City
import util.Constant

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
        val caribbean = "https://dbdzm869oupei.cloudfront.net/img/photomural/preview/42062.png"
        fun getFake(): TravelDetail {
            return TravelDetail(
                "Amazing travel to barcelona", caribbean, "Spain", "I went to carabian sea alone.\n" +
                        "there was shark there and that shark wanted to eat me but fortunately somehow I ran away", Constant.CON_BASE_TEMPLATE_URL, City.getFakes(), caribbean, " https ://persian5.cdn.asset.aparat.com/aparat-video/bdaea06a8c07f580a463d150bb9730af44790032-240p.mp4?wmsAuthSign=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6ImQxNWVmMmM1NDgxMGQzMDIzYTkzZDZlM2IwNzIyOGNlIiwiZXhwIjoxNjU4MjQ4NjEzLCJpc3MiOiJTYWJhIElkZWEgR1NJRyJ9.Y121L3MRjZjjxywwgAulf1gaeaClRRB0fs4LPoh1NA0"
                        "there was shark there and that shark wanted to eat me but fortunately somehow I ran away", GOOGLE_URL, City.getFakes(), caribbean, "https://persian5.asset.aparat.com/aparat-video/bdaea06a8c07f580a463d150bb9730af44790032-360p.mp4?wmsAuthSign=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6IjY1NjdmMzU0NzQ0NjM3Mjk2NjUxMjAwZjBiOTIzNTYzIiwiZXhwIjoxNjU4NTY2OTk3LCJpc3MiOiJTYWJhIElkZWEgR1NJRyJ9.YjP8cKAnu_mBXpEnAd3dUZvSDzEimHKEhn_cdcmUVbA"
            )
        }

        fun getFakes(): List<TravelDetail> {
            return listOf(
                TravelDetail(countryName = "Brazil", cover = "https://natureconservancy-h.assetsadobe.com/is/image/content/dam/tnc/nature/en/photos/coast-Carriacou-Grenada-MarjoAho.jpg?crop=0%2C283%2C4000%2C2100&wid=1200&hei=630&scl=3.3333333333333335", viewType = VIEW_TYPE_COVER),
                TravelDetail(image = caribbean, viewType = VIEW_TYPE_IMAGE),
                TravelDetail(description = "there was shark there and that shark wanted to eat me but fortunately somehow I ran away", viewType = VIEW_TYPE_DESCRIPTION),
                TravelDetail(link = "https://www.google.com", viewType = VIEW_TYPE_LINK),
                TravelDetail(video = "https://persian5.asset.aparat.com/aparat-video/bdaea06a8c07f580a463d150bb9730af44790032-360p.mp4?wmsAuthSign=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6IjY1NjdmMzU0NzQ0NjM3Mjk2NjUxMjAwZjBiOTIzNTYzIiwiZXhwIjoxNjU4NTY2OTk3LCJpc3MiOiJTYWJhIElkZWEgR1NJRyJ9.YjP8cKAnu_mBXpEnAd3dUZvSDzEimHKEhn_cdcmUVbA", viewType = VIEW_TYPE_VIDEO),
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
