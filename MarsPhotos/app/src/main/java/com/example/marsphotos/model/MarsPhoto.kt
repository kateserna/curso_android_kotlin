package com.example.marsphotos.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MarsPhoto(
    val id: String,
    @SerialName(value = "img_src") // Serializa la propiedad con el nombre "img_src" porque "img_src" es el nombre en el JSON
    val imgSrc: String
)