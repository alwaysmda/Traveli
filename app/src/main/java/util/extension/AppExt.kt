package util.extension

import util.Constant
import util.PrefManager

suspend fun PrefManager.authorize(ifAuthorized: suspend () -> (Unit), ifNotAuthorized: suspend () -> (Unit)) {
    if (getStringPref(Constant.PREF_TOKEN) == null) {
        ifNotAuthorized()
    } else {
        ifAuthorized()
    }
}