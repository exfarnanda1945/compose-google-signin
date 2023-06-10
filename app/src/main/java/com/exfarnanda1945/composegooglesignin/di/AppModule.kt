package com.exfarnanda1945.composegooglesignin.di

import com.exfarnanda1945.composegooglesignin.data.repository.AuthRepository
import com.exfarnanda1945.composegooglesignin.domain.repository.IAuthRepository
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {
    @Provides
    fun provideAuthRepository(
        onTapClient: SignInClient,
        buildSignInClient: BeginSignInRequest,
        auth: FirebaseAuth,
    ): IAuthRepository = AuthRepository(onTapClient, buildSignInClient, auth)
}