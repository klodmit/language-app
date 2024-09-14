package ru.languageapp.logic

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class EncryptSharedPreferencesManager(context: Context) {

    private val mainKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val encryptedTokenPreferences = EncryptedSharedPreferences.create(
        "token_preferences",
        mainKey, context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private val encryptedTokenEditor = encryptedTokenPreferences.edit()

    private val encryptedUserPreferences = EncryptedSharedPreferences.create(
        "user_preferences",
        mainKey, context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private val encryptedUserEditor = encryptedUserPreferences.edit()

    private val keyToken = "token"

    var token: String
        get() = encryptedTokenPreferences.getString(keyToken, "default_token").toString()
        set(value) {
            encryptedTokenEditor.putString(keyToken, value).apply()
        }

    private val keyLogin = "login"
    private val keyFirstName = "firstname"
    private val keyLastName = "lastname"
    private val keyUserEmail = "email"
    private val keyPoints = "points"

    var userLogin: String
        get() = encryptedUserPreferences.getString(keyLogin, "default_username").toString()
        set(value) {
            encryptedUserEditor.putString(keyLogin, value).apply()
        }

    var userFirstName: String
        get() = encryptedUserPreferences.getString(keyFirstName, "default_firstname").toString()
        set(value) {
            encryptedUserEditor.putString(keyFirstName, value).apply()
        }

    var userLastName: String
        get() = encryptedUserPreferences.getString(keyLastName, "default_lastname").toString()
        set(value) {
            encryptedUserEditor.putString(keyLastName, value).apply()
        }

    var userEmail: String
        get() = encryptedUserPreferences.getString(keyUserEmail, "default_email").toString()
        set(value) {
            encryptedUserEditor.putString(keyUserEmail, value).apply()
        }

    var userPoints: Int
        get() = encryptedUserPreferences.getInt(keyPoints, 0)
        set(value) {
            encryptedUserEditor.putInt(keyPoints, value).apply()
        }
}
