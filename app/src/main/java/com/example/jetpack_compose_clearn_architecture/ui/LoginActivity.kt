package com.example.jetpack_compose_clearn_architecture.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpack_compose_clearn_architecture.R
import com.example.jetpack_compose_clearn_architecture.ui.theme.JetpackcomposeclearnarchitectureTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

class LoginActivity : ComponentActivity() {
    private val TAG = "PRT"
    private val modifierContent = Modifier.fillMaxWidth()
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContent {
            JetpackcomposeclearnarchitectureTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Greeting("Android")
                    LoginCard()
                }
            }
        }
    }

    private fun simpleObservable() {
        val list = listOf<String>("12", "45", "67", "89")
        val observable = Observable.fromIterable(list)

        observable.subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                Log.d("PRT", "onSubscribe: ")
            }

            override fun onError(e: Throwable) {
                Log.d("PRT", "onError: ")
            }

            override fun onComplete() {
                Log.d("PRT", "onComplete: ")
            }

            override fun onNext(t: String) {
                Log.d("PRT", "onNext: ")
            }
        })
    }

    @Composable
    fun LoginCard() {
        val emailState = remember {
            mutableStateOf("prt@gmail.com")
        }
        val passwordState = remember {
            mutableStateOf("")
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.weight(1.7f),
                verticalArrangement = Arrangement.Center,
            )
            {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = stringResource(R.string.logo),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            ElevatedCard(
                modifier = Modifier
                    .weight(1.3f),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary),
                shape = RoundedCornerShape(
                    15.dp,
                    15.dp,
                    0.dp,
                    0.dp
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                Column(
                    modifier = modifierContent
                        .fillMaxHeight()
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Enter Login details",
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    InputTextField(
                        placeholder = stringResource(id = R.string.enter_email),
                        modifier = modifierContent,
                        emailState
                    )
                    Spacer(modifier = Modifier.height(15.dp))

                    InputTextField(
                        placeholder = stringResource(id = R.string.enter_password),
                        modifier = modifierContent,
                        passwordState
                    )

                    Spacer(modifier = Modifier.height(30.dp))
                    ActionButton(stringResource(R.string.login)) {
                        loginButton(emailState.value, passwordState.value)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    SpannableText(
                        stringResource(R.string.don_t_have_an_account),
                        stringResource(R.string.sign_up)
                    )
                }
            }
        }
    }

    private fun loginButton(email: String, password: String) {
        /*lifecycleScope.launch {
            Log.d(TAG, "loginButton: Clicked")
        }*/
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", it.exception)
                }
            }
    }

    @Composable
    fun ActionButton(
        btnText: String,
        click: () -> Unit
    ) {
        ElevatedButton(
            shape = MaterialTheme.shapes.medium,
            elevation = ButtonDefaults.buttonElevation(),
            colors = ButtonDefaults.elevatedButtonColors(Color.LightGray),
            modifier = modifierContent,
            onClick = click
        ) {
            Text(
                modifier = Modifier.padding(vertical = 5.dp),
                text = btnText,
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }

    @Composable
    fun SpannableText(stringResource1: String, stringResource2: String) {
        ClickableText(
            text = getAnnotedString(stringResource1, stringResource2),
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(fontSize = 16.sp, textAlign = TextAlign.Center),
            onClick = {
                //Toast.makeText(, "Clicked",Toast.LENGTH_SHORT)
                println("Clicked")
                startActivity(Intent(this, RegisterActivity::class.java))
            })
    }

    private fun getAnnotedString(
        stringResource1: String,
        stringResource2: String
    ): AnnotatedString {
        return buildAnnotatedString {
            append(stringResource1)
            withStyle(
                style = SpanStyle(
                    color = Color.Blue,
                    fontSize = 18.sp,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append(stringResource2)
            }
        }
    }

    @Composable
    fun InputTextField(placeholder: String, modifier: Modifier, textState: MutableState<String>) {
        OutlinedTextField(
            modifier = modifier,
            shape = RoundedCornerShape(15.dp),
            value = textState.value,
            singleLine = true,
            label = { Text(text = placeholder) },
            onValueChange = {
                textState.value = it
            })
    }

    @Composable
    @Preview
    fun PreviewView() {
        LoginCard()
    }
}