package br.unifor.cct.recyclerview.repository

import android.util.Log
import br.unifor.cct.recyclerview.model.RandomUser
import br.unifor.cct.recyclerview.service.RandomUserService
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object RandomUserRepository {
    private val randomUserService:RandomUserService

    init{
        val retrofit = Retrofit.Builder().baseUrl("https://randomuser.me")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()

        randomUserService = retrofit.create(RandomUserService::class.java)
    }

    fun getRandomUsers(quantity:Int=1):RandomUser?{

        var randomUser:RandomUser? = null

        if(quantity>=1){
            val call = randomUserService.getRandomUser(quantity)
            val response = call.execute()
            randomUser = response.body()

        }else{
            Log.e(RandomUserRepository::class.qualifiedName,"[ERROR] the quantity parameter must be 1 or more")
        }

        return randomUser

    }

}