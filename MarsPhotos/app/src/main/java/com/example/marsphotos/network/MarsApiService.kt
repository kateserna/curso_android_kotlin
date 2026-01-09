package com.example.marsphotos.network

import com.example.marsphotos.model.MarsPhoto
import retrofit2.http.GET

// Define como Retrofit se comunica con el servidor web con los métodos HTTP.
interface MarsApiService {

    // Obtiene la lista de fotos de Mars.
    @GET("photos")
    suspend fun getPhotos(): List<MarsPhoto>

}
