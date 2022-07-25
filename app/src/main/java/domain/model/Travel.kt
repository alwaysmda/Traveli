package domain.model


data class Travel(
    val id: Int,
    val name: String,
    val image: String,
    val country: Country,
    val isBookmarked: Boolean,
    val price: Long,
    val details: MutableList<TravelDetail>,
    val cityList: List<City>,
    val user: User
)
