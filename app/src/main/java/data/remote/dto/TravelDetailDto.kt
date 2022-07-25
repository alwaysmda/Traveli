package data.remote.dto

import domain.model.TravelDetail.Companion.VIEW_TYPE_COVER
import domain.model.TravelDetail.Companion.VIEW_TYPE_DESCRIPTION
import domain.model.TravelDetail.Companion.VIEW_TYPE_IMAGE
import domain.model.TravelDetail.Companion.VIEW_TYPE_LINK
import domain.model.TravelDetail.Companion.VIEW_TYPE_VIDEO

data class TravelDetailDto(
    val type: Int,
    val title: String?,
    val url: String?,
    val description: String?,
) {

    companion object {
        fun getFakes() = mutableListOf(
            TravelDetailDto(VIEW_TYPE_COVER, "Travel to brazil", "https://media.istockphoto.com/photos/palm-trees-on-tropical-beach-in-the-virgin-islands-picture-id183275871?b=1&k=20&m=183275871&s=170667a&w=0&h=_b6uD0KgaFqK3vQgH13_O-M5D5vFWobWrJK3rHyXtq4=", ""),
            TravelDetailDto(VIEW_TYPE_IMAGE, "", "https://www.konnecthq.com/wp-content/uploads/2019/07/Caribbean-Sea-31-12-1.jpg", ""),
            TravelDetailDto(VIEW_TYPE_DESCRIPTION, "", "", "I went to carabian sea alone. there was shark there and that shark wanted to eat me but fortunately somehow I ran away"),
            TravelDetailDto(VIEW_TYPE_LINK, "", "https://www.google.com", ""),
            TravelDetailDto(VIEW_TYPE_VIDEO, "", "https://caspian3.cdn.asset.aparat.com/aparat-video/bdaea06a8c07f580a463d150bb9730af44790032-360p.mp4?wmsAuthSign=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6IjFkMjU0MWI3NzI2ZDEzNzRmY2Q1ZDhjMWU0MjNhNWI2IiwiZXhwIjoxNjU4NjU2MzgyLCJpc3MiOiJTYWJhIElkZWEgR1NJRyJ9.mm0aOtMk5uZFZTs-U2szif-kP3wLndv94vlONMhY5_s", "")
        )
    }

}
