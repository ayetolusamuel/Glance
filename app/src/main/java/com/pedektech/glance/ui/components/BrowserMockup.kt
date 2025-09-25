package com.pedektech.glance.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.pedektech.glance.data.model.LinkPreviewData
import com.pedektech.glance.ui.theme.AppColors

@Composable
fun BrowserMockup(
    previewData: LinkPreviewData?,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.BrowserHeader),
        modifier = modifier
            .fillMaxWidth()
            .height(240.dp)
    ) {
        Column {
            // Browser header with navigation dots
            BrowserHeader()

            // Content area
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppColors.TealBackground),
                contentAlignment = Alignment.Center
            ) {
                PreviewImage(previewData?.imageUrl)
            }
        }
    }
}

@Composable
private fun BrowserHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(AppColors.BrowserHeader)
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            repeat(3) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(AppColors.BrowserDot)
                )
            }
        }
    }
}

@Composable
private fun PreviewImage(imageUrl: String?) {
    Box(
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        imageUrl?.let { url ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(url)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.FillBounds,
                contentDescription = "Preview image for the link",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
            )
        }
    }
}