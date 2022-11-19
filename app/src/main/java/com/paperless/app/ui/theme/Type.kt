package com.paperless.app.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.paperless.app.R

val fonts = FontFamily(
    Font(R.font.uniform_bold, weight = FontWeight.Bold),
    Font(R.font.uniform_regular, weight = FontWeight.Normal),
    Font(R.font.uniform_light, weight = FontWeight.Light),
    Font(R.font.unitform_semibold, weight = FontWeight.SemiBold),
    Font(R.font.uniform_italic, style = FontStyle.Italic)
)

val Typography.paperless_font: Typography

    get() = Typography(
        defaultFontFamily = fonts,
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
        h3 = TextStyle(
            fontSize = 32.sp,
            lineHeight = 32.sp,
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