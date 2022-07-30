package domain.usecase.user

import main.ApplicationClass
import util.Constant
import util.PrefManager

class LogoutUseCase(private val app: ApplicationClass, private val prefManager: PrefManager) {
    operator fun invoke() {
        prefManager.setPref(Constant.PREF_TOKEN, null)
        app.user = null
    }
}