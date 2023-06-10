package com.exfarnanda1945.composegooglesignin.presentation.sign_in

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.exfarnanda1945.composegooglesignin.utils.Screen
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    navHostController: NavHostController,
) {
    val viewModel = hiltViewModel<SignInViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // For Error Handling
    LaunchedEffect(key1 = state.signInError) {
        if (state.signInError != null) {
            Toast.makeText(context, state.signInError, Toast.LENGTH_LONG).show()
        }
    }

    // If already signed user will direct into profile screen
    LaunchedEffect(key1 = Unit) {
        if (viewModel.signedUser != null) {
            navHostController.navigate(Screen.ProfileScreen.route)
        }
    }

    // Start sign in intent
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == ComponentActivity.RESULT_OK) {
                scope.launch {
                    val singInResult = viewModel.signInWithIntent(
                        intent = result.data ?: return@launch
                    )

                    viewModel.onSignInResult(singInResult)
                }
            }
        }
    )

    // If sign in result success
    LaunchedEffect(key1 = state.isSignInSuccessful) {
        if (state.isSignInSuccessful) {
            Toast.makeText(
                context,
                "Sign in Successful",
                Toast.LENGTH_SHORT
            ).show()
            navHostController.navigate(Screen.ProfileScreen.route)
            viewModel.resetState()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp), contentAlignment = Alignment.Center
    ) {
        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            scope.launch {
                val signInIntentSender = viewModel.signIn()
                launcher.launch(
                    IntentSenderRequest.Builder(
                        signInIntentSender ?: return@launch
                    ).build()
                )
            }
        }) {
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                AsyncImage(
                    model = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/53/Google_%22G%22_Logo.svg/2008px-Google_%22G%22_Logo.svg.png",
                    contentDescription = "Sign In With Google",
                    modifier = Modifier
                        .height(24.dp)
                        .width(24.dp)
                        .padding(end = 8.dp)
                )
                Text(text = "Sign In With Google")
            }
        }

    }

}