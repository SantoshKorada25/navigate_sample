package com.santosh.navigate_sample

data class User(
    val id:Int,
    val name:String,
    val password:String
)


//In Real apps this would simulate from room/Retrofit/Cache
class UserRepository  {
    companion object {
        val users = listOf<User>(
            User(1, "Santosh" , "123456"),
            User(2, "Vijay" , "7268733"),
            User(3, "Praveen" , "357587"),
            User(4, "Sagar" , "986326"),
            User(5, "Naveen" , "9876876"),
            User(6, "Ramesh" , "3543657"),
            User(7, "Hari" , "8978597"),
            User(8, "Surendra" , "235435")
        )
    }


    fun getUserById(id:Int):User? {
        return users.find { it.id ==id }
    }
}