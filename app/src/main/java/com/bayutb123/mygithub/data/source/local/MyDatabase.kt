package com.bayutb123.mygithub.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bayutb123.mygithub.domain.model.User


@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class MyDatabase() : RoomDatabase() {
    abstract fun usersDao(): UsersDao
}