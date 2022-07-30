package data.remote.dto

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
            TravelDetailDto(VIEW_TYPE_IMAGE, "", "https://www.konnecthq.com/wp-content/uploads/2019/07/Caribbean-Sea-31-12-1.jpg", ""),
            TravelDetailDto(VIEW_TYPE_DESCRIPTION, "", "", "I went to carabian sea alone. there was shark there and that shark wanted to eat me but fortunately somehow I ran away"),
            TravelDetailDto(VIEW_TYPE_LINK, "", "https://www.google.com", ""),
            TravelDetailDto(VIEW_TYPE_VIDEO, "", "https://caspian3.cdn.asset.aparat.com/aparat-video/bdaea06a8c07f580a463d150bb9730af44790032-360p.mp4?wmsAuthSign=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6IjFkMjU0MWI3NzI2ZDEzNzRmY2Q1ZDhjMWU0MjNhNWI2IiwiZXhwIjoxNjU4NjU2MzgyLCJpc3MiOiJTYWJhIElkZWEgR1NJRyJ9.mm0aOtMk5uZFZTs-U2szif-kP3wLndv94vlONMhY5_s", ""),
            TravelDetailDto(VIEW_TYPE_VIDEO, "", "https://caspian3.cdn.asset.aparat.com/aparat-video/bdaea06a8c07f580a463d150bb9730af44790032-360p.mp4?wmsAuthSign=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6IjFkMjU0MWI3NzI2ZDEzNzRmY2Q1ZDhjMWU0MjNhNWI2IiwiZXhwIjoxNjU4NjU2MzgyLCJpc3MiOiJTYWJhIElkZWEgR1NJRyJ9.mm0aOtMk5uZFZTs-U2szif-kP3wLndv94vlONMhY5_s", "")

        )
    }

}
