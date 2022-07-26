package data.repository

import data.remote.ApiResponseHandler
import data.remote.DataState
import data.remote.UserApi
import data.remote.dto.*
import domain.model.Stat
import domain.model.User
import domain.model.UserPreview
import domain.repository.UserRepository
import main.ApplicationClass
import util.Constant
import util.PrefManager

class UserRepositoryImpl(
    private val userApi: UserApi,
    private val app: ApplicationClass,
    private val prefManager: PrefManager,
    private val networkErrorMapper: NetworkErrorMapper,
    private val userPreviewMapper: UserPreviewMapper,
    private val userMapper: UserMapper,
    private val statMapper: StatMapper,
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
        return prefManager.getStringPref(Constant.PREF_TOKEN)?.let {
            when (val response = call { userApi.getMe() }) {
                is DataState.Failure -> response
                is DataState.Success -> DataState.Loading
                is DataState.Loading -> {
                    val data = ResponseGetUserDto.getFake(1)
                    DataState.Success(userMapper.toDomainModel(data.user))
                }
            }
        } ?: kotlin.run {
            DataState.Failure(DataState.Failure.CODE_NOT_FOUND, "")
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
}