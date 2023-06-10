package com.exfarnanda1945.composegooglesignin.data.repository

import android.content.Intent
import android.content.IntentSender
import com.exfarnanda1945.composegooglesignin.data.model.SignInResult
import com.exfarnanda1945.composegooglesignin.data.model.UserData
import com.exfarnanda1945.composegooglesignin.domain.repository.IAuthRepository
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException
import javax.inject.Inject

@ViewModelScoped
class AuthRepository @Inject constructor(
    private val onTapClient: SignInClient,
    private val buildSignInClient: BeginSignInRequest,
    private val auth: FirebaseAuth,
): IAuthRepository {

    override val getSignedUser: UserData?
        get() = auth.currentUser?.let {
            UserData(
                userId = it.uid,
                username = it.displayName,
                profilePictureUrl = it.photoUrl.toString()
            )
        }

    override suspend fun signIn(): IntentSender? {
        val result = try {
            onTapClient.beginSignIn(buildSignInClient).await()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }

        return result?.pendingIntent?.intentSender
    }

    override suspend fun signOut() {
        try {
            onTapClient.signOut().await()
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    override suspend fun signInWithIntent(intent: Intent): SignInResult {
        val credential = onTapClient.getSignInCredentialFromIntent(intent)
        val googleToken = credential.googleIdToken
        val googleCredential = GoogleAuthProvider.getCredential(googleToken, null)

        return try {
            val result = auth.signInWithCredential(googleCredential).await()
            val user = result.user
            SignInResult(
                data = user?.let {
                    UserData(
                        userId = it.uid,
                        username = it.displayName,
                        profilePictureUrl = it.photoUrl.toString()
                    )
                },
                errorMessage = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            SignInResult(
                null, e.message
            )
        }
    }

}