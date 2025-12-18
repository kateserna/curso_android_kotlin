package com.example.marsphotos.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL =
    "https://android-kotlin-fun-mars-server.appspot.com"

// Compilador Retrofit, para compilar y crear un objeto Retrofit.
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

// Define como Retrofit se comunica con el servidor web con los métodos HTTP.
interface MarsApiService {

    // Obtiene la lista de fotos de Mars.
    @GET("photos")
    suspend fun getPhotos(): String

}

// Crea el objeto Retrofit que implementa la interfaz MarsApiService.
object MarsApi {
    // Crea el objeto Retrofit que implementa la interfaz MarsApiService.
    val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}