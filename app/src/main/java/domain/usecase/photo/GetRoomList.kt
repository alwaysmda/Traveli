package domain.usecase.photo

import data.remote.DataState
import domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRoomList @Inject constructor(private val repository: PhotoRepository) {
    operator fun invoke(clientId: String): Flow<DataState<ArrayList<String>>> = flow {
        emit(DataState.Loading)
        val result = repository.getRooms(clientId)
        emit(result)
    }
}