package data.repository

import data.remote.ApiResponseHandler
import data.remote.DataState
import data.remote.UserApi
import data.remote.dto.NetworkErrorMapper
import data.remote.dto.ResponseStatDto
import data.remote.dto.StatMapper
import domain.model.Stat
import domain.model.User
import domain.repository.UserRepository
import main.ApplicationClass

class UserRepositoryImpl(
    private val userApi: UserApi,
    private val app: ApplicationClass,
    private val networkErrorMapper: NetworkErrorMapper,
    private val statMapper: StatMapper,
) : UserRepository, ApiResponseHandler(app, networkErrorMapper) {
    override suspend fun getUsers(query: String): DataState<List<User>> {
        return when (val response = call { userApi.getUsers() }) {
            is DataState.Failure -> response
            is DataState.Loading -> DataState.Loading
            is DataState.Success -> DataState.Success(User.getFakes())
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