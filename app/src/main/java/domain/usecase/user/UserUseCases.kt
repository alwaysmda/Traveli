package domain.usecase.user

data class UserUseCases(
    val searchUserUseCase: SearchUserUseCase,
    val getUserStatUseCase: GetUserStatUseCase,
    val getMeUseCase: GetMeUseCase,
    val getUserUseCase: GetUserUseCase,
    val getUserTravelListUseCase: GetUserTravelListUseCase,
    val updateUserInfoUseCase: UpdateUserInfoUseCase,
    val updateCoverUseCase: UpdateCoverUseCase,
    val updateAvatarUseCase: UpdateAvatarUseCase,
    val updateContactUseCase: UpdateContactUseCase,
)