package com.evren.repository.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.evren.repository.model.entities.user.UserEntity

@Dao
internal abstract class UserDao {

    //region Queries
    @Query("SELECT * FROM UserEntity WHERE id=1 LIMIT 1")
    abstract suspend fun getUserEntity(): UserEntity?

    //endregion

    //region Insertion
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun saveUserEntity(userEntity: UserEntity)

    //endregion

}