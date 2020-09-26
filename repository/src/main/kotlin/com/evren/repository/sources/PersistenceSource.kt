package com.evren.repository.sources

import android.content.Context
import com.evren.repository.model.entities.token.TokenEntity
import com.evren.repository.interactors.base.Failure
import com.evren.repository.interactors.base.PersistenceEmpty
import com.evren.repository.interactors.base.Result
import com.evren.repository.interactors.base.Success
import com.evren.repository.model.entities.user.UserEntity
import com.evren.repository.persistence.UniversityAppDatabase

import javax.inject.Inject

/**
 * Persistance source using Room database to save / read objects for SST - offline usage
 */
class PersistenceSource @Inject constructor(
    ctx: Context
)  {

    //region Functions

    private val universityAppDatabase = UniversityAppDatabase.getInstance(ctx)


    suspend fun getUser(): Result<UserEntity> =
        universityAppDatabase
            .userDao
            .getUserEntity()
            .takeIf { it != null }
            ?.run {
                Success(this)
            } ?: Failure(PersistenceEmpty())


    suspend fun saveUser(userEntity: UserEntity) {
        universityAppDatabase.userDao.saveUserEntity(userEntity)
    }


     suspend fun getToken(): Result<TokenEntity> =
        universityAppDatabase
            .tokenDao
            .getTokenEntity()
            .takeIf { it != null }
            ?.run {
                Success(this)
            } ?: Failure(PersistenceEmpty())


     suspend fun saveToken(tokenEntity: TokenEntity) {
        universityAppDatabase.tokenDao.saveTokenEntity(tokenEntity)
    }

     fun saveTokenSync(tokenEntity: TokenEntity) = universityAppDatabase.tokenDao.saveTokenEntitySync(tokenEntity)
    //endregion
}
