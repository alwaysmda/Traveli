package domain.model.travel

import domain.model.City

data class TravelDetail(
    val name: String,
    val image:String,
    val countryName: String,
    val description: String,
    val cities: List<City>,
    val images: List<String>,
    val video: String
){
    companion object{
        val caribbean = "https://www.konnecthq.com/caribbean-sea/"
        fun getFake():TravelDetail{
            return TravelDetail("Amazing travel to barcelona", caribbean,"Spain","I went to carabian sea alone.\n" +
                    "there was shark there and that shark wanted to eat me but fortunately somehow I ran away", City.getFakes(), listOf(caribbean),"https://persian5.cdn.asset.aparat.com/aparat-video/bdaea06a8c07f580a463d150bb9730af44790032-240p.mp4?wmsAuthSign=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6ImQxNWVmMmM1NDgxMGQzMDIzYTkzZDZlM2IwNzIyOGNlIiwiZXhwIjoxNjU4MjQ4NjEzLCJpc3MiOiJTYWJhIElkZWEgR1NJRyJ9.Y121L3MRjZjjxywwgAulf1gaeaClRRB0fs4LPoh1NA0")
        }
    }

}
