package domain.usecase.user

data class UserUseCases(
    val searchUser: SearchUser,
    val getUserStat: GetUserStat,
    val getMe: GetMe,
    val getUser: GetUser,
)