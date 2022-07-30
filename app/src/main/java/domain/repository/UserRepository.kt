package domain.repository

import data.remote.DataState
import domain.model.*

interface UserRepository {
    suspend fun searchUser(query: String, page: Int): DataState<List<UserPreview>>
    suspend fun getMe(): DataState<User>
    suspend fun getUser(userId: Long): DataState<User>
    suspend fun getUserStat(userId: Long): DataState<List<Stat>>
    suspend fun getUserTravelList(userId: Long, page: Int): DataState<List<TravelPreview>>
    suspend fun updateCover(filePath: String): DataState<User>
    suspend fun updateAvatar(filePath: String): DataState<User>
    suspend fun updateUserInfo(user: User): DataState<User>
    suspend fun updateContact(contactItem: ContactItem): DataState<User>
    suspend fun deleteAccount(): DataState<Unit>
}