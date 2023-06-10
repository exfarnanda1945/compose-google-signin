package com.exfarnanda1945.composegooglesignin.presentation.sign_in

import android.content.Intent
import android.content.IntentSender
import androidx.lifecycle.ViewModel
import com.exfarnanda1945.composegooglesignin.data.model.SignInResult
import com.exfarnanda1945.composegooglesignin.domain.repository.IAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: IAuthRepository,
): ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    val signedUser = authRepository.getSignedUser

    fun onSignInResult(result: SignInResult){
        _state.update { it.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
        ) }
    }

    fun resetState(){
        _state.update { SignInState() }
    }

    suspend fun signInWithIntent(intent: Intent):SignInResult{
        return authRepository.signInWithIntent(intent = intent)
    }

    suspend fun signIn(): IntentSender?{
        return authRepository.signIn()
    }
}