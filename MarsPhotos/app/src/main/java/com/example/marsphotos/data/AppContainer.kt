package com.example.marsphotos.data

import com.example.marsphotos.network.MarsApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val marsPhotosRepository: MarsPhotosRepository
}

class DefaultAppContainer: AppContainer {
    private val baseUrl =
        "https://android-kotlin-fun-mars-server.appspot.com"

    // Compilador Retrofit, para compilar y crear un objeto Retrofit.
    /**
     * Use el generador de Retrofit para crear un objeto de retrofit mediante un convertidor de serialización de Kotlinx.
     */
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    // Crea el objeto Retrofit que implementa la interfaz MarsApiService.
    private val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }

    // Proporciona una instancia del repositorio de fotos de Mars. Anula la propiedad marsPhotosRepository
    override val marsPhotosRepository: MarsPhotosRepository by lazy {
        NetworkMarsPhotosRepository(retrofitService)
    }
}
