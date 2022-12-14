package domain.usecase.photo

import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
data class PhotoUseCases(
    val downloadUseCase: DownloadUseCase,
    val getPhoto: GetPhoto,
    val getPhotoList: GetPhotoList,
    val getClient: GetClientList,
    val getRoomList: GetRoomList,
    val getRoomUserList: GetRoomUserList,
)
