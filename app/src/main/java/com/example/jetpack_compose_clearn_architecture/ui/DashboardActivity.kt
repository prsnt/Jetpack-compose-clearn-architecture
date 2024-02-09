package com.example.jetpack_compose_clearn_architecture.ui

import android.content.Intent
import android.graphics.drawable.Icon
import android.graphics.drawable.VectorDrawable
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpack_compose_clearn_architecture.utils.PreferenceManager
import com.example.jetpack_compose_clearn_architecture.data.model.RegisterModel
import com.example.jetpack_compose_clearn_architecture.ui.theme.JetpackcomposeclearnarchitectureTheme
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DashboardActivity : ComponentActivity() {
    private val TAG = "DashboardActivity"
    lateinit var auth: FirebaseAuth
    private var user: RegisterModel? = null

    private val items = listOf(
        Screens("screen1", " Screen 1", Icons.Default.AccountBox),
        Screens("screen2", " Screen 2", Icons.Default.Star),
        Screens("screen3", " Screen 3", Icons.Default.DateRange),
        Screens("screen4", " Screen 4", Icons.Default.Notifications)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        user = PreferenceManager.getModel<RegisterModel>("USER_DATA")
        setContent {
            JetpackcomposeclearnarchitectureTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val currentUser = auth.currentUser
                    if (currentUser != null)
                        DashboardCard()
                    else
                        startActivity(Intent(this, LoginActivity::class.java))
                }
            }
        }
    }

    data class Screens(val route: String, val title: String, val icon: ImageVector)

    @Composable
    fun DashboardCard() {
        val navController = rememberNavController()
        val snackbarHoststate = remember {
            SnackbarHostState()
        }
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        ModalNavigationDrawer(drawerContent = {
            ModalDrawerSheet {

            }
        }, drawerState = drawerState) {
            Scaffold(
                bottomBar = { MyBottomBar(navController = navController) },
                topBar = {
                    MyToolbar(title = "Dashboard") {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                },
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHoststate)
                },
                floatingActionButton = {
                    FloatingActionButton(onClick = {
                        showSnackbar("New List Clicked", scope, snackbarHoststate)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add New List"
                        )
                    }
                },
                content = {
                    Column(
                        modifier = Modifier
                            .padding(it),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        NavHost(navController = navController, startDestination = "screen1") {
                            composable("screen1") { Screen1(navController = navController) }
                            composable("screen2") { Screen2(navController = navController) }
                            composable("screen3") { Screen3(navController = navController) }
                            composable("screen4") { Screen4(navController = navController) }
                        }
                    }
                }
            )
        }
    }

    private fun showSnackbar(
        text: String,
        scope: CoroutineScope,
        snackbarHostState: SnackbarHostState
    ) {
        scope.launch {
            snackbarHostState.showSnackbar(text)
        }
    }

    @Composable
    fun MyBottomBar(navController: NavController) {
        BottomAppBar {
            items.forEach {
                IconButton(onClick = {
                    navController.navigate(it.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }) {
                    Icon(imageVector = it.icon, contentDescription = "My Account")
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MyToolbar(
        title: String,
        onNavigateUp: () -> Unit
    ) {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            navigationIcon = {
                IconButton(onClick = onNavigateUp) {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu Button")
                }
            },
        )
    }

    @Composable
    fun Screen1(navController: NavController) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Screen 1")
        }
    }

    @Composable
    fun Screen2(navController: NavController) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Screen 2")
        }
    }

    @Composable
    fun Screen3(navController: NavController) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Screen 3")
        }
    }

    @Composable
    fun Screen4(navController: NavController) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Screen 4")
        }
    }
}