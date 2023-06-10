package com.exfarnanda1945.composegooglesignin.presentation.profile

import androidx.lifecycle.ViewModel
import com.exfarnanda1945.composegooglesignin.domain.repository.IAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: IAuthRepository
): ViewModel() {
    val signedUser = authRepository.getSignedUser

    suspend fun signOut(){
        authRepository.signOut()
    }
}