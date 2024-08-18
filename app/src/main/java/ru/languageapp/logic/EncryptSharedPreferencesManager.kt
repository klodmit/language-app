package ru.languageapp.logic

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys


class EncryptSharedPreferencesManager(context: Context) {
    private val mainKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val encryptedSharedPreferences = EncryptedSharedPreferences.create(
        "user",
        mainKey,context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private val encryptedEditor = encryptedSharedPreferences.edit()

    private val keyLogin = "login"
    private val keyPassword = "password"
    private val keyToken = "token"

    var login
        get() = encryptedSharedPreferences.getString(keyLogin,"").toString()
        set(value){
            encryptedEditor.putString(keyLogin,value)
            encryptedEditor.commit()
        }

    var password
        get() = encryptedSharedPreferences.getString(keyPassword,"").toString()
        set(value){
            encryptedEditor.putString(keyPassword,value)
            encryptedEditor.commit()
        }

    var token
        get() = encryptedSharedPreferences.getString(keyToken,"").toString()
        set(value){
            encryptedEditor.putString(keyToken,value)
            encryptedEditor.commit()
        }
}