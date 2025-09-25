//package com.pedektech.glance
//
//import android.util.Log
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.BasicTextField
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Share
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.material3.Typography
//import androidx.compose.material3.lightColorScheme
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.shadow
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import coil.compose.AsyncImage
//import coil.request.ImageRequest
//import com.pedektech.glance.MainActivity.LinkPreviewData
//import com.pedektech.glance.data.model.LinkPreviewData
//
//object AppColors {
//    val Background = Color(0xFFF5F5F5)
//    val CardBackground = Color.White
//    val TealBackground = Color(0xFF3DBAA3)
//    val TextPrimary = Color(0xFF1A1A1A)
//    val TextSecondary = Color(0xFF666666)
//    val ShareButtonBlue = Color(0xFF1E3A8A)
//    val InputBackground = Color.White
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AppTheme(content: @Composable () -> Unit) {
//    MaterialTheme(
//        colorScheme = lightColorScheme(
//            background = AppColors.Background,
//            surface = AppColors.CardBackground,
//            primary = AppColors.TealBackground,
//            onBackground = AppColors.TextPrimary,
//            onSurface = AppColors.TextPrimary
//        ),
//        typography = Typography(
//            bodyLarge = TextStyle(
//                fontSize = 16.sp,
//                fontWeight = FontWeight.Normal,
//                color = AppColors.TextPrimary
//            ),
//            bodyMedium = TextStyle(
//                fontSize = 14.sp,
//                fontWeight = FontWeight.Normal,
//                color = AppColors.TextSecondary,
//                lineHeight = 20.sp
//            ),
//            headlineMedium = TextStyle(
//                fontSize = 28.sp,
//                fontWeight = FontWeight.Bold,
//                color = AppColors.TextPrimary
//            )
//        ),
//        content = content
//    )
//}
//
//@Composable
//fun LinkInputField(
//    value: String,
//    onValueChange: (String) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Card(
//        modifier = modifier
//            .shadow(2.dp, RoundedCornerShape(24.dp)),
//        shape = RoundedCornerShape(24.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = AppColors.InputBackground
//        )
//    ) {
//        Box(
//            modifier = Modifier.padding(20.dp),
//            contentAlignment = Alignment.CenterStart
//        ) {
//            if (value.isEmpty()) {
//                Text(
//                    text = "Paste a link...",
//                    style = MaterialTheme.typography.bodyLarge.copy(
//                        color = Color(0xFF999999),
//                        fontSize = 18.sp
//                    )
//                )
//            }
//            BasicTextField(
//                value = value,
//                onValueChange = onValueChange,
//                textStyle = MaterialTheme.typography.bodyLarge.copy(
//                    fontSize = 18.sp,
//                    color = AppColors.TextPrimary
//                ),
//                keyboardOptions = KeyboardOptions.Default.copy(
//                    imeAction = ImeAction.Done
//                ),
//                modifier = Modifier.fillMaxWidth()
//            )
//        }
//    }
//}
//
//@Composable
//fun TechCrunchPreviewCard(
//    modifier: Modifier = Modifier,
//    previewData: LinkPreviewData?
//) {
//
//    LaunchedEffect(previewData) {
//        Log.e("Preview", "TechCrunchPreviewCard: $previewData", )
//    }
//    Card(
//        modifier = modifier
//            .shadow(4.dp, RoundedCornerShape(16.dp)),
//        shape = RoundedCornerShape(16.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = AppColors.CardBackground
//        )
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp)
//        ) {
//            // Browser mockup with TechCrunch content
//            BrowserMockup(previewData)
//
//            Spacer(modifier = Modifier.height(20.dp))
//
//            // Title
//            Text(
//                text = previewData?.title?:"Glance",
//                style = MaterialTheme.typography.headlineMedium.copy(
//                    fontSize = 32.sp,
//                    fontWeight = FontWeight.Bold
//                )
//            )
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            // Description
//            Text(
//                text = previewData?.description?:"Glance Description",
//                style = MaterialTheme.typography.bodyMedium.copy(
//                    fontSize = 16.sp,
//                    lineHeight = 24.sp,
//                    color = AppColors.TextSecondary
//                )
//            )
//        }
//    }
//}
//
//@Composable
//fun BrowserMockup(previewData: LinkPreviewData?) {
//    Card(
//        shape = RoundedCornerShape(8.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = Color(0xFFE5E5E5)
//        ),
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(240.dp)
//    ) {
//        Column {
//            // Browser header
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(32.dp)
//                    .background(Color(0xFFD1D1D1))
//            ) {
//                Row(
//                    modifier = Modifier
//                        .align(Alignment.CenterStart)
//                        .padding(horizontal = 12.dp),
//                    horizontalArrangement = Arrangement.spacedBy(6.dp)
//                ) {
//                    repeat(3) {
//                        Box(
//                            modifier = Modifier
//                                .size(8.dp)
//                                .clip(CircleShape)
//                                .background(Color(0xFF999999))
//                        )
//                    }
//                }
//            }
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(AppColors.TealBackground),
//                contentAlignment = Alignment.Center
//            ) {
//                Box(
//                    modifier = Modifier
//                        .size(120.dp)
//                        .clip(CircleShape)
//                        .background(Color.White),
//                    contentAlignment = Alignment.Center
//                ) {
//                    previewData?.imageUrl?.let {
//                        AsyncImage(
//                            model = ImageRequest.Builder(LocalContext.current)
//                                .data(previewData?.imageUrl)
//                                .crossfade(true)
//                                .build(),
//                            contentScale = ContentScale.FillBounds,
//                            contentDescription = "Preview Image",
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .clip(RoundedCornerShape(16.dp))
//                   )
//                    }
//
//                }
//            }
//        }
//    }//https://www.youtube.com/watch?v=rLiIKmoZlhU
//}
//
//@Composable
//fun ShareActionButton(
//    isPressed: Boolean,
//    onPressedChange: (Boolean) -> Unit,
//    onClick: () -> Unit
//) {
//    Box(
//        modifier = Modifier
//            .size(64.dp)
//            .shadow(
//                elevation = if (isPressed) 4.dp else 8.dp,
//                shape = CircleShape
//            )
//            .clip(CircleShape)
//            .background(AppColors.ShareButtonBlue)
//            .clickable {
//                onPressedChange(true)
//                onClick()
//                onPressedChange(false)
//            },
//        contentAlignment = Alignment.Center
//    ) {
//        Icon(
//            imageVector = Icons.Default.Share,
//            contentDescription = "Share",
//            tint = Color.White,
//            modifier = Modifier.size(24.dp)
//        )
//    }
//}