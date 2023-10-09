package com.bayutb123.mygithub.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bayutb123.mygithub.domain.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {
    @Query("SELECT * FROM users")
    fun getUsers(): Flow<List<User>>

    @Query("SELECT * FROM users WHERE login LIKE '%' || :query || '%'")
    fun searchUsers(query: String): Flow<List<User>>

    @Insert
    suspend fun insertUser(users: User)

    @Delete
    suspend fun deleteUser(users: User)

}