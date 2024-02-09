package com.example.jetpack_compose_clearn_architecture.data

import com.example.jetpack_compose_clearn_architecture.data.model.RegisterModel
import io.reactivex.rxjava3.core.Observable

interface APIServices
{
    fun loginService(): Observable<RegisterModel>
    fun registerService(): Observable<RegisterModel>
}