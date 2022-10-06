package com.auyon.lab10rickmorty_joseauyon

import androidx.room.*

@Dao
interface CharacterDao {

    @Query("SELECT * FROM character")
    suspend fun getUsers(): List<Character>

    @Query("SELECT * FROM character WHERE id = :id")
    suspend fun getUserById(id: Int): Character?

    @Update
    suspend fun update(user: Character)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: Character)

    @Delete
    suspend fun delete(character: Character): Int

    @Query("DELETE FROM character")
    suspend fun deleteAll(): Int

}