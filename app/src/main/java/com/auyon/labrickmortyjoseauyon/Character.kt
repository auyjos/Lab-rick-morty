package com.auyon.labrickmortyjoseauyon

import java.io.Serializable


data class Character(
    val name: String,
    val species: String,
    val status: String,
    val gender: String,
    val image: String
) :Serializable

enum class CharacterType{
    HUMAN,
    ALIEN
}
