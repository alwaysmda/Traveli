package domain.model.travel

import kotlin.random.Random

data class Banner(
    val banner:Travel,
    val subBanner:List<Travel>
){
    companion object{
        const val rioImage = "https://upload-gifs.s3-sa-east-1.amazonaws.com/ae79d48c-5994-449a-a10a-f02a0ea455b0_Cristo+Redentor+Riotur.jpg"
        fun getFake() = Banner(Travel(Random.nextInt().toString(),"Rio", rioImage), listOf(Travel(Random.nextInt().toString(),"Rio", rioImage)))
    }

}
