package data.repository

import data.remote.ApiResponseHandler
import data.remote.DataState
import data.remote.PhotoApi
import data.remote.dto.NetworkErrorMapper
import data.remote.dto.PhotoMapper
import domain.model.Photo
import domain.repository.PhotoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import main.ApplicationClass
import javax.inject.Inject

@ExperimentalCoroutinesApi
class PhotoRepositoryImpl @Inject constructor(
    private val app: ApplicationClass,
    private val api: PhotoApi,
    private val networkErrorMapper: NetworkErrorMapper,
    private val photoMapper: PhotoMapper,
) : ApiResponseHandler(app, networkErrorMapper), PhotoRepository {

    override suspend fun download(url: String, path: String, name: String, data: String?) = channelFlow {
        call(path, name, api.download(url), data).collectLatest {
            send(it)
        }
    }

    override suspend fun getPhotoList(page: Int): DataState<List<Photo>> {
        return when (val response = call(api.getPhotoList(page))) {
            is DataState.Loading -> response
            is DataState.Failure -> response
            is DataState.Success -> {
                val data = photoMapper.fromEntityList(response.data)
                DataState.Success(data)
            }
        }
    }

    override suspend fun getPhoto(id: Int): DataState<Photo> {
        return when (val response = call(api.getPhoto(id))) {
            is DataState.Loading -> response
            is DataState.Failure -> response
            is DataState.Success -> {
                val data = photoMapper.toDomainModel(response.data)
                DataState.Success(data)
            }
        }
    }

    override suspend fun getClients(): DataState<ArrayList<String>> {
        return when (val response = call(api.getClient())) {
            is DataState.Loading -> response
            is DataState.Failure -> response
            is DataState.Success -> {
                //                val result = arrayListOf<String>()
                //                for(index in 0 until response.data.length()){
                //                    result.add(response.data[index].toString())
                //                }
                DataState.Success(response.data)
            }
        }
    }

    override suspend fun getRooms(clientId: String): DataState<ArrayList<String>> {
        return when (val response = call(api.getRooms("https://chat-node.cslsrvr.com/roomsOf/$clientId"))) {
            is DataState.Loading -> response
            is DataState.Failure -> response
            is DataState.Success -> {
                DataState.Success(response.data)
            }
        }
    }

    override suspend fun getRoomsUsers(roomId: String): DataState<ArrayList<String>> {
        return when (val response = call(api.getRooms("https://chat-node.cslsrvr.com/inRoom/$roomId"))) {
            is DataState.Loading -> response
            is DataState.Failure -> response
            is DataState.Success -> {
                DataState.Success(response.data)
            }
        }
    }
}