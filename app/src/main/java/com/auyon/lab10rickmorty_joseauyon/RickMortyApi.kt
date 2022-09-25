package com.auyon.lab10rickmorty_joseauyon

import com.auyon.lab10rickmorty_joseauyon.Character
import com.auyon.lab10rickmorty_joseauyon.CharactersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RickMortyApi {

    @GET("/api/character")
    fun getCharacters(): Call<CharactersResponse>

    @GET("/api/character/{id}")
    fun getCharacter(
        @Path("id") id: Int
    ): Call<Character>

}