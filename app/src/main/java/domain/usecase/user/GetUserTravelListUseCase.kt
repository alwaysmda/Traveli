package domain.usecase.user

import data.remote.DataState
import domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetUserTravelListUseCase(private val userRepository: UserRepository) {
    operator fun invoke(userId: Long, page: Int) = flow {
        emit(DataState.Loading)
        emit(userRepository.getUserTravelList(userId, page))
        //        when(val res = userRepository.getUserTravelList(userId,page)){
        //            is DataState.Loading->emit(res)
        //            is DataState.Failure->emit(res)
        //            is DataState.Success->{
        //                if(app.user?.id == userId){
        //                    emit(DataState.Success(res.data.plus(TravelPreview.getAddItem())))
        //                }else{
        //                    emit(res)
        //                }
        //            }
        //        }
    }.flowOn(Dispatchers.IO)
}