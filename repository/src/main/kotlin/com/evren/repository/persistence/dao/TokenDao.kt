package com.evren.repository.persistence.dao

import androidx.room.*
import com.evren.repository.model.entities.token.TokenEntity


@Dao
internal abstract class TokenDao {

    //region Queries
    @Query("SELECT * FROM TokenEntity WHERE id=1 LIMIT 1")
    abstract suspend fun getTokenEntity(): TokenEntity?

    //endregion

    //region Insertion
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun saveTokenEntity(tokenEntity: TokenEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveTokenEntitySync(tokenEntity: TokenEntity)

    //endregion

}