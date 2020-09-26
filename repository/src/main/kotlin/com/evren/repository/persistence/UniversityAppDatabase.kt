package com.evren.repository.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.evren.repository.model.entities.token.TokenEntity
import com.evren.repository.model.entities.user.UserEntity
import com.evren.repository.persistence.dao.TokenDao
import com.evren.repository.persistence.dao.UserDao

const val DB_NAME = "UniversityAppDB"

/**
 * DB that manages launches
 */
@Database(
    entities = [TokenEntity::class, UserEntity::class],
    exportSchema = true,
    version = 1
)

internal abstract class UniversityAppDatabase : RoomDatabase() {

    //region Companion

    companion object {

        private lateinit var instance: UniversityAppDatabase

        fun getInstance(ctx: Context): UniversityAppDatabase {
            if (!::instance.isInitialized) {
                instance = Room.databaseBuilder(ctx, UniversityAppDatabase::class.java, DB_NAME)
                    .build()
            }

            return instance
        }

    }
    //endregion

    //region Abstractions


    internal abstract val userDao: UserDao

    internal abstract val tokenDao : TokenDao
    //endregion
}
