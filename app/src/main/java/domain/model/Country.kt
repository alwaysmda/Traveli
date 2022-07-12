package domain.model

import kotlin.random.Random

data class Country(
    val id:Int,
    val name:String,
    val image:String
){
    companion object{
        val takhtJamshidImage = "https://rashintravel.com/wp-content/uploads/2019/12/Day-17-Persepolis.jpg"
        fun getFake() = Country(Random.nextInt(),"Iran", takhtJamshidImage)
        fun getFakes():List<Country> {
            val countries = mutableListOf<Country>()
            repeat(10){
                countries.add(getFake())
            }
            return countries
        }
    }
}
