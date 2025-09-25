package com.pedektech.glance.ui.theme


import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val AppTypography: Typography
    get() = Typography(
        // Use MaterialTheme's typography as base and override specific styles
        headlineLarge = TextStyle(
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.TextPrimary
        ),
        headlineMedium = TextStyle(
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.TextPrimary
        ),
        bodyLarge = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = AppColors.TextPrimary
        ),
        bodyMedium = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = AppColors.TextSecondary,
            lineHeight = 24.sp
        ),
        bodySmall = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = AppColors.TextSecondary,
            lineHeight = 20.sp
        ),
        labelLarge = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = AppColors.PlaceholderText
        )
    )