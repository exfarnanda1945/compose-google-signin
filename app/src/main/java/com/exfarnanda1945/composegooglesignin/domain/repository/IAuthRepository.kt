package com.exfarnanda1945.composegooglesignin.domain.repository

import android.content.Intent
import android.content.IntentSender
import com.exfarnanda1945.composegooglesignin.data.model.SignInResult
import com.exfarnanda1945.composegooglesignin.data.model.UserData

interface IAuthRepository {
    val getSignedUser: UserData?
    suspend fun signIn(): IntentSender?
    suspend fun signOut()
    suspend fun signInWithIntent(intent: Intent): SignInResult
}