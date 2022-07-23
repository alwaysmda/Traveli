package domain.repository

import data.remote.DataState
import domain.model.Stat
import domain.model.User

interface UserRepository {
    suspend fun getUsers(query: String): DataState<List<User>>

    suspend fun getUserStat(userId: Long): DataState<List<Stat>>
}