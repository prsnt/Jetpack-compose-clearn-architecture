package com.example.jetpack_compose_clearn_architecture.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpack_compose_clearn_architecture.R
import com.example.jetpack_compose_clearn_architecture.utils.Resource
import com.example.jetpack_compose_clearn_architecture.utils.Status
import com.example.jetpack_compose_clearn_architecture.ui.theme.JetpackcomposeclearnarchitectureTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : ComponentActivity() {

    private val TAG = "PRT"
    private val modifierContent = Modifier.fillMaxWidth()
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContent {
            JetpackcomposeclearnarchitectureTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    RegisterCard()
                }
            }
        }
    }

    @Composable
    fun RegisterCard(registerModel: RegisterViewModel = viewModel()) {
        val registerUiState by registerModel.registerFlow.collectAsState(initial = Resource(Status.LOADING,null,""))

        val emailState = remember {
            mutableStateOf("")
        }
        val passwordState = remember {
            mutableStateOf("")
        }
        val repeatPwdState =  remember{
            mutableStateOf("")
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(
                        id
                        = R.drawable.logo
                    ),
                    contentDescription = stringResource(id = R.string.logo)
                )
            }
            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(horizontal = 20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                InputTextCommon(stringResource(R.string.enter_email), emailState)
                Spacer(modifier = Modifier.height(15.dp))
                InputTextCommon(stringResource(R.string.enter_password), passwordState)
                Spacer(modifier = Modifier.height(15.dp))
                InputTextCommon(stringResource(R.string.repeat_password), repeatPwdState)
                Spacer(modifier = Modifier.height(25.dp))
                ActionButton(
                    btnText = stringResource(R.string.sign_up)
                ) {
                    registerModel.registerUserAuth(emailState.value, passwordState.value)
                }
                Spacer(modifier = Modifier.height(15.dp))
                if(registerUiState.status == Status.SUCCESS)
                    startActivity(Intent(this@RegisterActivity, DashboardActivity::class.java))
                Toast.makeText(this@RegisterActivity, registerUiState.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    @Composable
    fun InputTextCommon(string: String, textState: MutableState<String>)
    {
        InputTextField(
            placeholder = string,
            modifier = modifierContent,
            textState = textState
        )
    }

    @Composable
    fun ActionButton(
        btnText: String,
        click: () -> Unit
    ) {
        ElevatedButton(
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.elevatedButtonColors(Color.LightGray),
            elevation = ButtonDefaults.buttonElevation(),
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
        RegisterCard()
    }
}