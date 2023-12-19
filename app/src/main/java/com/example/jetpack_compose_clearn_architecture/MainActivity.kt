package com.example.jetpack_compose_clearn_architecture

import android.graphics.fonts.FontStyle
import android.os.Bundle
import android.text.Editable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpack_compose_clearn_architecture.ui.theme.JetpackcomposeclearnarchitectureTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackcomposeclearnarchitectureTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Greeting("Android")
                    LoginCard()
                }
            }
        }
    }
}

@Composable
fun LoginCard() {
    val modifierContent = Modifier.fillMaxWidth()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.weight(1.7f),  //important
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
                    modifierContent = modifierContent
                )
                Spacer(modifier = Modifier.height(15.dp))

                InputTextField(
                    placeholder = stringResource(id = R.string.enter_password),
                    modifierContent = modifierContent
                )

                Spacer(modifier = Modifier.height(30.dp))
                ElevatedButton(
                    shape = MaterialTheme.shapes.medium,
                    elevation = ButtonDefaults.buttonElevation(),
                    modifier = modifierContent,
                    onClick = { }) {
                    Text(
                        modifier = Modifier.padding(vertical = 8.dp),
                        text = stringResource(R.string.login),
                        style = TextStyle.Default
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun InputTextField(placeholder: String, modifierContent: Modifier) {
    OutlinedTextField(
        modifier = modifierContent,
        shape = RoundedCornerShape(15.dp),
        value = "",
        placeholder = { Text(text = placeholder) },
        onValueChange = {})
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetpackcomposeclearnarchitectureTheme {
        //Greeting("Android")
        LoginCard()
    }
}