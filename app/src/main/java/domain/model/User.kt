package domain.model

import kotlin.random.Random

data class User(
    val id:Int,
    val name:String,
    val profileImage:String,
    ){

    companion object{
        const val denisImage = "https://upload.wikimedia.org/wikipedia/commons/2/23/Dennis_Ritchie_2011.jpg"
        fun getFake() = User(Random.nextInt(),"Denis", denisImage)
        fun getFakes(): List<User> {
            val users = mutableListOf<User>()
            repeat(10){
                users.add(getFake())
            }
            return users
        }
    }

}
