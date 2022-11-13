package com.paperless.app.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography.paperless_font: Typography
    get() = Typography(
        body1 = TextStyle(
            fontSize = 14.sp,
            lineHeight = 14.sp,
            letterSpacing = 0.sp

        ),
        body2 = TextStyle(
            fontSize = 13.sp,
            lineHeight = 13.sp,
            letterSpacing = 0.sp,

            ),
        h4 = TextStyle(
            fontSize = 25.sp,
            lineHeight = 25.sp,
            letterSpacing = 0.sp

        ),
        h5 = TextStyle(
            fontSize = 18.sp,
            lineHeight = 18.sp,
            letterSpacing = 0.sp

        ),
        caption =  TextStyle(
            fontSize = 11.sp,
            lineHeight = 11.sp,
            letterSpacing = 0.sp
        )
    )

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)