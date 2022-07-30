package data.repository

import data.remote.ApiResponseHandler
import data.remote.DataState
import data.remote.UserApi
import data.remote.dto.*
import domain.model.*
import domain.repository.UserRepository
import main.ApplicationClass
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import util.PrefManager
import java.io.File

class UserRepositoryImpl(
    private val userApi: UserApi,
    private val app: ApplicationClass,
    private val prefManager: PrefManager,
    private val networkErrorMapper: NetworkErrorMapper,
    private val userPreviewMapper: UserPreviewMapper,
    private val userMapper: UserMapper,
    private val statMapper: StatMapper,
    private val travelPreviewMapper: TravelPreviewMapper,
) : UserRepository, ApiResponseHandler(app, networkErrorMapper) {
    override suspend fun searchUser(query: String, page: Int): DataState<List<UserPreview>> {
        return when (val response = call { userApi.searchUser(query, page) }) {
            is DataState.Failure -> response
            is DataState.Success -> DataState.Loading
            is DataState.Loading -> {
                val data = ResponseSearchUserDto.getFake()
                DataState.Success(userPreviewMapper.fromEntityList(data.userPreviewList))
            }
        }
    }

    override suspend fun getMe(): DataState<User> {
        return when (val response = call { userApi.getMe() }) {
            is DataState.Failure -> response
            is DataState.Success -> DataState.Loading
            is DataState.Loading -> {
                val data = ResponseGetUserDto.getFake(1)
                DataState.Success(userMapper.toDomainModel(data.user))
            }
        }
    }

    override suspend fun getUser(userId: Long): DataState<User> {
        return when (val response = call { userApi.getUser(userId) }) {
            is DataState.Failure -> response
            is DataState.Success -> DataState.Loading
            is DataState.Loading -> {
                val data = ResponseGetUserDto.getFake()
                DataState.Success(userMapper.toDomainModel(data.user))
            }
        }
    }

    override suspend fun getUserStat(userId: Long): DataState<List<Stat>> {
        return when (val response = call { userApi.getUserStat(userId) }) {
            is DataState.Failure -> response
            is DataState.Success -> DataState.Loading
            is DataState.Loading -> {
                val data = ResponseStatDto.getFake()
                DataState.Success(statMapper.fromEntityList(data.statList))
            }
        }
    }

    override suspend fun getUserTravelList(userId: Long, page: Int): DataState<List<TravelPreview>> {
        return when (val response = call { userApi.getUserTravelList(userId, page) }) {
            is DataState.Failure -> response
            is DataState.Success -> DataState.Loading
            is DataState.Loading -> {
                val data = ResponseTravelListDto.getFake()
                DataState.Success(travelPreviewMapper.fromEntityList(data.travels))
            }
        }
    }

    override suspend fun updateCover(filePath: String): DataState<User> {
        val file = File(filePath)
        val requestFile = file.asRequestBody("image".toMediaTypeOrNull())
        val body = MultipartBody.Part.create(requestFile)
        return when (val response = call { userApi.updateCover(body) }) {
            is DataState.Failure -> response
            is DataState.Success -> DataState.Loading
            is DataState.Loading -> {
                val data = ResponseGetUserDto.getFake(1)
                DataState.Success(userMapper.toDomainModel(data.user))
            }
        }
    }

    override suspend fun updateAvatar(filePath: String): DataState<User> {
        val file = File(filePath)
        val requestFile = file.asRequestBody("image".toMediaTypeOrNull())
        val body = MultipartBody.Part.create(requestFile)
        return when (val response = call { userApi.updateAvatar(body) }) {
            is DataState.Failure -> response
            is DataState.Success -> DataState.Loading
            is DataState.Loading -> {
                val data = ResponseGetUserDto.getFake(1)
                DataState.Success(userMapper.toDomainModel(data.user))
            }
        }
    }

    override suspend fun updateUserInfo(user: User): DataState<User> {
        return when (val response = call { userApi.updateUserInfo(user.name ?: "", user.bio, user.country.id, user.city) }) {
            is DataState.Failure -> response
            is DataState.Success -> DataState.Loading
            is DataState.Loading -> {
                val data = ResponseGetUserDto.getFake(1)
                DataState.Success(user)
            }
        }
    }

    override suspend fun updateContact(contactItem: ContactItem): DataState<User> {
        return when (val response = call { userApi.updateContact(contactItem.type.name, contactItem.value) }) {
            is DataState.Failure -> response
            is DataState.Success -> DataState.Loading
            is DataState.Loading -> {
                val data = ResponseGetUserDto.getFake(1)
                DataState.Success(userMapper.toDomainModel(data.user))
            }
        }
    }

    override suspend fun deleteAccount(): DataState<Unit> {
        return when (val response = call { userApi.deleteAccount() }) {
            is DataState.Failure -> response
            is DataState.Success -> DataState.Loading
            is DataState.Loading -> {
                DataState.Success(Unit)
            }
        }
    }
}