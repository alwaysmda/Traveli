package domain.model.travel

data class Banner(
    val banner:Travel,
    val subBanner:List<Banner>
)
