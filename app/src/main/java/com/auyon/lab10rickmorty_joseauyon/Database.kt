package com.auyon.lab10rickmorty_joseauyon

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Character::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun charDao(): CharacterDao
}