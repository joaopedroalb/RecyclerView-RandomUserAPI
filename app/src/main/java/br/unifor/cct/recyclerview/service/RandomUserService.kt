package br.unifor.cct.recyclerview.service

import br.unifor.cct.recyclerview.model.RandomUser
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserService {

    @GET("/api")
    fun getRandomUser(@Query("results")quantity:Int = 1):retrofit2.Call<RandomUser>

}