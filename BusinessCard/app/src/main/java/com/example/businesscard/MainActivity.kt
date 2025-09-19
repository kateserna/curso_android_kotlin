package com.example.businesscard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Email
import androidx.compose.material.icons.twotone.Phone
import androidx.compose.material.icons.twotone.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.businesscard.ui.theme.BusinessCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusinessCardTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BusinessCard()
                }
            }
        }
    }
}

@Composable
fun NameCard(name: String, profession: String, color: Color, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier // El modifier se pasa desde BusinessCard para controlar su posición
            .fillMaxWidth()
            .padding(16.dp), // // Padding general para NameCard
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val imageLogo = painterResource(R.drawable.android_logo)
        Image(
            painter = imageLogo,
            contentDescription = "logo Android",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape) // imagen redonda
                .background(Color(0xFF073042))
        )
        Text(
            text = name,
            fontSize = 32.sp,
            modifier = Modifier.padding(top = 16.dp),
            textAlign = TextAlign.Center //Alinear el texto al centro
        )
        Text(
            text = profession,
            fontWeight = FontWeight.Bold,
            color = color,
            modifier = Modifier.padding(top = 8.dp),
            textAlign = TextAlign.Center

        )
    }
}

@Composable
fun InfoContact(modifier: Modifier = Modifier) { // Añadimos un modifier para controlar su posición
    Column(
        modifier = modifier // El modifier se pasa desde BusinessCard
            .width(IntrinsicSize.Max)
            //.background(Color.Green)
            .padding(bottom = 32.dp, start = 40.dp, end = 40.dp), // Padding inferior y laterales para el bloque
        horizontalAlignment = Alignment.Start // Alinea el contenido de las filas al inicio
    ) {
        // Fila 1: Teléfono
        ContactRow(
            icon = Icons.TwoTone.Phone,
            iconDescription = stringResource(R.string.icon_row1),
            text = stringResource(R.string.phone_number)
        )
        Spacer(modifier = Modifier.height(8.dp)) // Espacio pequeño entre filas
        //Fila 2: compartir
        ContactRow(
            icon = Icons.TwoTone.Share,
            iconDescription = stringResource(R.string.icon_row2),
            text = stringResource(R.string.description_row2),
        )
        Spacer(modifier = Modifier.height(8.dp)) // Espacio pequeño entre filas
        //Fila 3: Email
        ContactRow(
            icon = Icons.TwoTone.Email,
            iconDescription = stringResource(R.string.icon_row3),
            text = stringResource(R.string.description_row3),
        )
    }
}

@Composable
fun ContactRow(icon: ImageVector, iconDescription: String, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically, // Alinea ícono y texto verticalmente en el centro de la fila
        modifier = Modifier.fillMaxWidth() // La fila ocupa todo el ancho disponible dentro de InfoContact
    ){
        Icon(
            imageVector = icon,
            tint = Color(0xFF3A0272),
            contentDescription = iconDescription,
            modifier = Modifier.padding(end = 16.dp) // Espacio entre el ícono y el texto
        )
        Text(
            text = text,
            textAlign = TextAlign.Start // El texto dentro de la fila se alinea al inicio
        )
    }
}

@Composable
fun BusinessCard() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x9EB69DF8)),
        horizontalAlignment = Alignment.CenterHorizontally // Centra NameCard horizontalmente
    ) {
        // NameCard ocupará el espacio superior y se centrará gracias al Arrangement.Center
        // y al weight del Spacer
        NameCard(
            name = stringResource(R.string.name),
            profession = stringResource(R.string.profession),
            color = Color(0xFF640BB9),
            modifier = Modifier.weight(1f) // NameCard toma el espacio disponible empujando InfoContact hacia abajo
        )
        // InfoContact se colocará debajo de NameCard.
        InfoContact()
    }
}

@Preview(showBackground = true)
@Composable
fun BusinessCardPreview() {
    BusinessCardTheme {
        BusinessCard()
    }
}