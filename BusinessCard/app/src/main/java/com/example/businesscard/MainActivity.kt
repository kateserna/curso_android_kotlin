package com.example.businesscard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) {
                    BusinessCard()
                }
            }
        }
    }

}

@Composable
fun NameCard(name: String, profession: String, color: Color, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 48.dp, bottom = 24.dp), // Añadido padding superior e inferior
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val imageLogo = painterResource(R.drawable.android_logo)
        Image(
            painter = imageLogo,
            contentDescription = "logo Android",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape) //.clip(RoundedCornerShape(16.dp)) - redondo
                .background(Color(0xFF073042))
        )
        Text(
            text = name,
            fontSize = 35.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = profession,
            fontWeight = FontWeight.Bold,
            color = color,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun InfoContact() {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp), //Espacio vertical general para el bloque de contacto
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Fila 1: Teléfono
        Row(
            modifier = Modifier
                .fillMaxWidth() // La fila ocupa todo el ancho
                .weight(1f) // Cada fila comparte el espacio vertical
                .padding(horizontal = 24.dp), // Margen interno para el contenido de la fila
            verticalAlignment = Alignment.CenterVertically // Alinea ícono y texto verticalmente
            ) {
            Icon(
                imageVector = Icons.TwoTone.Phone,
                tint = Color(0xFF3A0272),
                contentDescription = "phone",
                modifier = Modifier.padding(end = 16.dp) // Ícono con espaciado a la derecha
            )
            Text(
                text = "+57 3164521698",
                textAlign = TextAlign.Start, // Texto alineado al inicio
                modifier = Modifier.weight(1f) // Texto ocupa el espacio restante
            )
        }
        //Fila 2: compartir
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically
            ) {
            Icon(
                imageVector = Icons.TwoTone.Share,
                tint = Color(0xFF3A0272),
                contentDescription = "share",
                modifier = Modifier.padding(end = 16.dp)
            )
            Text(
                text = "@kateserna",
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(1f)
            )
        }
        //Fila 3: Email
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically
            ) {
            Icon(
                imageVector = Icons.TwoTone.Email,
                tint = Color(0xFF3A0272),
                contentDescription = "email",
                modifier = Modifier.padding(end = 16.dp)
            )
            Text(
                text = "kserna.g@gmail.com",
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun BusinessCard() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            . background(Color(0x9EB69DF8)),
    ) {
        NameCard(
            name = stringResource(R.string.name),
            profession = stringResource(R.string.profession),
            color = Color(0xFF640BB9)
        )
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