package util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.xodus.traveli.BuildConfig
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import main.ApplicationClass

class PrefManager(val app: ApplicationClass) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = BuildConfig.APPLICATION_ID)

    fun getStringPref(key: String, default: String? = null): String? {
        return runBlocking { app.dataStore.data.catch { emit(emptyPreferences()) }.map { it[stringPreferencesKey(key)] }.first() ?: default }
    }

    fun getBooleanPref(key: String, default: Boolean = false): Boolean {
        return runBlocking { app.dataStore.data.catch { emit(emptyPreferences()) }.map { it[booleanPreferencesKey(key)] }.first() ?: default }
    }

    fun getIntPref(key: String, default: Int = 0): Int {
        return runBlocking { app.dataStore.data.catch { emit(emptyPreferences()) }.map { it[intPreferencesKey(key)] }.first() ?: default }
    }

    fun getLongPref(key: String, default: Long = 0L): Long {
        return runBlocking { app.dataStore.data.catch { emit(emptyPreferences()) }.map { it[longPreferencesKey(key)] }.first() ?: default }
    }

    fun getFloatPref(key: String, default: Float = 0F): Float {
        return runBlocking { app.dataStore.data.catch { emit(emptyPreferences()) }.map { it[floatPreferencesKey(key)] }.first() ?: default }
    }

    fun setPref(key: String, value: Any) {
        when (value) {
            is String  -> runBlocking { app.dataStore.edit { it[stringPreferencesKey(key)] = value } }
            is Boolean -> runBlocking { app.dataStore.edit { it[booleanPreferencesKey(key)] = value } }
            is Int     -> runBlocking { app.dataStore.edit { it[intPreferencesKey(key)] = value } }
            is Long    -> runBlocking { app.dataStore.edit { it[longPreferencesKey(key)] = value } }
            is Float   -> runBlocking { app.dataStore.edit { it[floatPreferencesKey(key)] = value } }
        }
    }
    //    private val pref: SharedPreferences = appClass.getSharedPreferences(CON_PREF_NAME, Context.MODE_PRIVATE)
    //
    //    fun getStringPref(key: String): String? {
    //        return try {
    //            val item = pref.getString(encPref(key), null)
    //            if (item == null) {
    //                null
    //            } else {
    //                val result = decPref(item)
    //                result.ifBlank { null }
    //            }
    //        } catch (e: Exception) {
    //            null
    //        }
    //    }
    //
    //    fun getBooleanPref(key: String): Boolean {
    //        return try {
    //            val item = pref.getString(encPref(key), null)
    //            if (item == null) {
    //                false
    //            } else {
    //                decPref(item).toBoolean()
    //            }
    //        } catch (e: Exception) {
    //            false
    //        }
    //    }
    //
    //    fun getIntPref(key: String): Int {
    //        return try {
    //            val item = pref.getString(encPref(key), null)
    //            if (item == null) {
    //                0
    //            } else {
    //                decPref(item).toInt()
    //            }
    //        } catch (e: Exception) {
    //            0
    //        }
    //    }
    //
    //    fun getLongPref(key: String): Long {
    //        return try {
    //            val item = pref.getString(encPref(key), null)
    //            if (item == null) {
    //                0L
    //            } else {
    //                decPref(item).toLong()
    //            }
    //        } catch (e: Exception) {
    //            0L
    //        }
    //    }
    //
    //    fun getFloatPref(key: String): Float {
    //        return try {
    //            val item = pref.getString(encPref(key), null)
    //            if (item == null) {
    //                0F
    //            } else {
    //                decPref(item).toFloat()
    //            }
    //        } catch (e: Exception) {
    //            0F
    //        }
    //    }
    //
    //    fun setPref(key: String, value: Any) {
    //        pref.edit().putString(encPref(key), encPref(value)).apply()
    //    }
    //
    //    private fun encPref(value: Any): String {
    //        return MCryptAES(CON_AES_KEY).encrypt(value.toString())
    //    }
    //
    //    private fun decPref(value: Any): String {
    //        return MCryptAES(CON_AES_KEY).decrypt(value.toString())
    //    }
}