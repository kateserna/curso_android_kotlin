package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color(0xFFDAF1F1) // 0xFFC0CFE0
                ) { innerPadding ->
                    ArtSpaceLayout(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ArtSpaceLayout(
    modifier: Modifier = Modifier
) {
    var currentArtPage by remember { mutableStateOf(1) } //estado de la pagina actual
    val totalArtPieces = 4 //total de piezas de arte (imagenes)

    //función para cambiar de obra a la siguiente
    val onNextClick = {
        currentArtPage = if (currentArtPage < totalArtPieces) {
            currentArtPage + 1
        } else {
            1
        }
    }

    //función para cambiar de obra a la anterior
    val onPreviousClick = {
        currentArtPage = if (currentArtPage > 1) {
            currentArtPage -1
        } else {
            totalArtPieces
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.tittle_app),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color(1, 29, 63, 255),
            modifier = Modifier.padding(16.dp)
        )
        //Pasamos el estado actual de la página al componente que muestra la imagen y la descripción
        ImageAndDescriptionChange(currentArtPage = currentArtPage)
        //Pasamos las funciones de clic al componente de botones
        PaginationButton(
            onNextClick = onNextClick,
            onPreviousClick = onPreviousClick,
            //se pasan los estados de paginacion
            currentPage = currentArtPage,
            totalPages = totalArtPieces
        )
    }
}

@Composable
fun ImageAndDescriptionChange(currentArtPage: Int, modifier: Modifier = Modifier) {
    //Pasamos el indice de imagen a mostrar en función de la página actual
    when (currentArtPage) {
        1 -> ArtImageAndDescription(
            imageResourceId = R.drawable.cat_4959941_1280__1_,
            contentDescriptionResourceId = R.string.content_image1,
            titleImageResourceId = R.string.content_image1,
            authorResourceId = R.string.author_image1,
            yearResourceId = R.string.age_image1
        )
        2 -> ArtImageAndDescription(
            imageResourceId = R.drawable.peacock_3080897_1280,
            contentDescriptionResourceId = R.string.content_image2,
            titleImageResourceId = R.string.content_image2,
            authorResourceId = R.string.author_image2,
            yearResourceId = R.string.age_image2
        )
        3 -> ArtImageAndDescription(
            imageResourceId = R.drawable.dolomites_2897602_1280,
            contentDescriptionResourceId = R.string.content_image3,
            titleImageResourceId = R.string.content_image3,
            authorResourceId = R.string.author_image3,
            yearResourceId = R.string.age_image3
        )
        4 -> ArtImageAndDescription(
            imageResourceId = R.drawable.sunset_8331285_1280,
            contentDescriptionResourceId = R.string.content_image4,
            titleImageResourceId = R.string.content_image4,
            authorResourceId = R.string.author_image4,
            yearResourceId = R.string.age_image4
        )
    }
}

//Componente de Imagen y descripción de la imagen
@Composable
fun ArtImageAndDescription(
    imageResourceId: Int,
    contentDescriptionResourceId: Int,
    titleImageResourceId: Int,
    authorResourceId: Int,
    yearResourceId: Int,
    modifier: Modifier = Modifier
) {
    Column {
        Surface(
            modifier = modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
            color = Color.White, //color del marco de la imagen
            // Para controlar la sombra del surface se utiliza shadowElevation.
            shadowElevation = 30.dp,
            //border = BorderStroke(1.dp, Color.Magenta)
        ) {
            Image(
                painter = painterResource(imageResourceId),
                contentDescription = stringResource(contentDescriptionResourceId),
                // 4. Usamos padding para crear el espacio en blanco del marco.
                modifier = Modifier.padding(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            shape = RoundedCornerShape(4.dp),
            color = Color(0xFFECECEC),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = stringResource(titleImageResourceId),
                    fontSize = 24.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = stringResource(authorResourceId),
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
                Text(
                    text = stringResource(yearResourceId),
                    color = Color.DarkGray
                )
            }
        }
    }
}

//Definicion del botón de paginado
@Composable
fun ActionButton(
    action: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    ElevatedButton(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(1, 29, 63, 255)),
        elevation = ButtonDefaults.elevatedButtonElevation(
            //control de la sombra el botón elevado
            defaultElevation = 10.dp,
            pressedElevation = 2.dp
        ),
        border = BorderStroke(2.dp, Color.Cyan),
        modifier = modifier
    ){
        Text(
            text = action,
            fontSize = 20.sp,
            color = Color.White
        )
    }
}

//Layout botones
@Composable
fun PaginationButton(
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit,
    //parametros de las paginas
    currentPage: Int,
    totalPages: Int,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        ActionButton(
            action = "Previous",
            onClick = onPreviousClick,
            enabled = currentPage > 1, //habilita el botón si se cumple la condición
            modifier = Modifier.weight(1f)

        )
        ActionButton(
            action = "Next",
            onClick = onNextClick,
            enabled = currentPage < totalPages, //habilita el botón si se cumple la condición
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ArtSpaceLayoutPreview() {
    ArtSpaceTheme {
        ArtSpaceLayout(modifier = Modifier.background(Color(0xFFDAF1F1)))
    }
}