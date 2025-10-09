package com.example.artspace

import android.icu.text.CaseMap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color(0xFFDAF1F1) // 0xFFC0CFE0
                    //containerColor = Color(16, 2, 44, 255) morado
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
fun ArtSpaceLayout(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Art Space",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color(1, 29, 63, 255),
            modifier = Modifier.padding(16.dp)
        )
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
            color = Color.White, //color del marco de la imagen
            // Para controlar la sombra del surface se utiliza shadowElevation.
            shadowElevation = 30.dp,
            //border = BorderStroke(1.dp, Color.Magenta)
        ) {
            Image(
                painter = painterResource(R.drawable.cat_4959941_1280__1_),
                contentDescription = null,
                // 4. Usamos padding para crear el espacio en blanco del marco.
                modifier = Modifier.padding(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        ArtDescription()
        PaginationButton()
    }
}
//Componente de descripción de la imagen
@Composable
fun ArtDescription() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
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
                text = "Imagen de gato",
                fontSize = 24.sp,
                color = Color.DarkGray
            )
            Text(
                text = "Maria Kalcheva en Pixabaay",
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )
            Text(
                text = "2020",
                color = Color.DarkGray
            )
        }
    }

}


//Definicion del botón de paginado
@Composable
fun ActionButton(
    action: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedButton(
        onClick = onClick,
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
fun PaginationButton() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        ActionButton(
            action = "Previous",
            onClick = { /*TODO*/ },
            modifier = Modifier.weight(1f)

        )
        ActionButton(
            action = "Next",
            onClick = { /*TODO*/ },
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ArtSpaceLayoutPreview() {
    ArtSpaceTheme {
        //ArtSpaceLayout()

        ArtSpaceLayout(modifier = Modifier.background(Color(0xFFDAF1F1)))
    }
}