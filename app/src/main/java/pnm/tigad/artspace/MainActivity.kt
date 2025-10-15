package pnm.tigad.artspace

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pnm.tigad.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFFFF9F9)
                ) {
                    ArtSpaceApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ArtSpaceApp() {
    var currentArtIndex by remember { mutableStateOf(0) }

    val artList = listOf(
        ArtPiece(R.drawable.art_1, "Anak rusa berbintik minum air, dikelilingi taman hutan bunga yang sangat cantik", "Zaty", "2021"),
        ArtPiece(R.drawable.art_2, "Lukisan dengan berbagai macam bunga warna cerah di dalam vas", "Zaty", "2023"),
        ArtPiece(R.drawable.art_3, "Dua angsa putih cantik sedang mandi di sungai", "Zaty", "2022"),
        ArtPiece(R.drawable.art_4, "Dermaga kayu panjang membentang ke danau, diterangi cahaya matahari terbenam dengan langit indah", "Zaty", "2022"),
        ArtPiece(R.drawable.art_5, "Seorang wanita di perahu, dikelilingi lentera terbang dan menara megah yang diterangi cahaya lampu lampion", "Zaty", "2023")
    )

    val currentArt = artList[currentArtIndex]
    val configuration = LocalConfiguration.current

    val buttonColor = Color(0xFFFF80AB)
    val softPink = Color(0xFFFFE4E9)

    //  MODE LANDSCAPE
    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White), // 🩶 Tambahan agar background gambar putih
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                AnimatedContent(
                    targetState = currentArt.imageRes,
                    transitionSpec = {
                        slideInHorizontally(initialOffsetX = { -300 }) + fadeIn(animationSpec = tween(600)) togetherWith
                                slideOutHorizontally(targetOffsetX = { 300 }) + fadeOut(animationSpec = tween(600))
                    },
                    label = "art transition"
                ) { image ->
                    Image(
                        painter = painterResource(id = image),
                        contentDescription = currentArt.title,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                AnimatedContent(
                    targetState = currentArt,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(400)) togetherWith fadeOut(animationSpec = tween(400))
                    },
                    label = "description transition"
                ) { art ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = softPink),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = art.title,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "${art.artist}, ${art.year}",
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            currentArtIndex =
                                if (currentArtIndex == 0) artList.size - 1 else currentArtIndex - 1
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(text = "Previous")
                    }

                    Button(
                        onClick = {
                            currentArtIndex =
                                if (currentArtIndex == artList.size - 1) 0 else currentArtIndex + 1
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(text = "Next")
                    }
                }
            }
        }

        //  MODE PORTRAIT
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White), // 🩶 Tambahan agar background gambar putih
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(8.dp)
            ) {
                AnimatedContent(
                    targetState = currentArt.imageRes,
                    transitionSpec = {
                        slideInHorizontally(initialOffsetX = { 300 }) + fadeIn(animationSpec = tween(600)) togetherWith
                                slideOutHorizontally(targetOffsetX = { -300 }) + fadeOut(animationSpec = tween(600))
                    },
                    label = "image transition"
                ) { image ->
                    Image(
                        painter = painterResource(id = image),
                        contentDescription = currentArt.title,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            AnimatedContent(
                targetState = currentArt,
                transitionSpec = {
                    fadeIn(animationSpec = tween(400)) togetherWith fadeOut(animationSpec = tween(400))
                },
                label = "desc transition"
            ) { art ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = softPink),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = art.title,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "${art.artist}, ${art.year}",
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        currentArtIndex =
                            if (currentArtIndex == 0) artList.size - 1 else currentArtIndex - 1
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Previous")
                }

                Button(
                    onClick = {
                        currentArtIndex =
                            if (currentArtIndex == artList.size - 1) 0 else currentArtIndex + 1
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Next")
                }
            }
        }
    }
}

data class ArtPiece(
    val imageRes: Int,
    val title: String,
    val artist: String,
    val year: String
)