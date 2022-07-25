package domain.model.travel

import kotlin.random.Random

data class Banner(
    val banner: TravelPreview,
    val subBanner: List<TravelPreview>
){
    companion object{
        const val rioImage = "https://upload-gifs.s3-sa-east-1.amazonaws.com/ae79d48c-5994-449a-a10a-f02a0ea455b0_Cristo+Redentor+Riotur.jpg"
        const val madagasImage = "https://images.blacktomato.com/2020/04/Madagascar-holidays-hero.jpg?auto=compress%2Cformat&fit=crop&h=627&ixlib=php-3.3.0&w=1200&s=c7ed0e9213275872a3351cbd96f21a2d"
        fun getFake() = Banner(TravelPreview(Random.nextInt(), "Rio", rioImage), listOf(TravelPreview(Random.nextInt(), "Madagascar", madagasImage)))
    }

}
