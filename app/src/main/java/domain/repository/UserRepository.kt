package domain.repository

import data.remote.DataState
import domain.model.Stat
import domain.model.User
import domain.model.UserPreview

interface UserRepository {
    suspend fun searchUser(query: String, page: Int): DataState<List<UserPreview>>
    suspend fun getUser(userId: Long): DataState<User>
    suspend fun getUserStat(userId: Long): DataState<List<Stat>>
    //    suspend fun getUserTravelList(userId: Long): DataState<List<Travel>>
}