package domain.usecase.photo

import data.remote.DataState
import domain.model.Photo
import domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPhotoList @Inject constructor(private val repository: PhotoRepository) {
     operator fun invoke(page: Int): Flow<DataState<List<Photo>>> = flow {
         emit(DataState.Loading)
         val result = repository.getPhotoList(page)
         emit(result)
     }
}