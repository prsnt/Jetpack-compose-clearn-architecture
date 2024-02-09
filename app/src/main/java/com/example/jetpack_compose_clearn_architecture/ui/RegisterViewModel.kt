package com.example.jetpack_compose_clearn_architecture.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack_compose_clearn_architecture.data.model.RegisterModel
import com.example.jetpack_compose_clearn_architecture.utils.PreferenceManager
import com.example.jetpack_compose_clearn_architecture.utils.Resource
import com.example.jetpack_compose_clearn_architecture.utils.Status
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class RegisterViewModel() : ViewModel() {
    private val _registerFlow = MutableSharedFlow<Resource<FirebaseUser>>()
    val registerFlow = _registerFlow.asSharedFlow()
    private var auth: FirebaseAuth = Firebase.auth
    val TAG = "RegisterViewModel"

    fun registerUserAuth(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                viewModelScope.launch {
                    if (it.isSuccessful) {
                        val model = RegisterModel()
                        model.apply {
                            id = it.result.user?.uid
                            name = it.result.user?.displayName
                            this.email = it.result.user?.email.toString()
                        }
                        PreferenceManager.putModel(model,"USER_DATA")
                        _registerFlow.emit(Resource(Status.SUCCESS, it.result.user, "Success"))
                    } else {
                        it.exception?.message?.let {
                            _registerFlow.emit(Resource(Status.ERROR, null, it))
                        }
                    }
                }
            }
    }

    /*fun callRegisterAPI(email: String, password: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://reqres.in/api")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
        val apiService = retrofit.create(APIServices::class.java)
        apiService.registerService()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it != null) {
                    viewModelScope.launch {
                        _registerFlow.emit(Resource(Status.SUCCESS, it, "Success"))
                    }
                    Log.d("PRT", "ID: " + it.id + "\n Token:" + it.token)
                }
            }
    }*/
}