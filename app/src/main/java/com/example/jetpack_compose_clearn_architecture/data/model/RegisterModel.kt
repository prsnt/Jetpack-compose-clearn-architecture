package com.example.jetpack_compose_clearn_architecture.data.model

import com.google.gson.annotations.SerializedName

data class RegisterModel(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("token")
    var token: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("email")
    var email: String? = null
)