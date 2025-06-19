package com.example.galleryscroll

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment

@Composable
fun GalleryScroll() {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Loop nama file dari dscf2925.jpg ke dscf2930.jpg
        for (i in 2925..2930) {
            val imageName = "dscf$i"
            val imageResId = context.resources.getIdentifier(imageName, "drawable", context.packageName)

            if (imageResId != 0) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = imageName,
                    modifier = Modifier
                        .padding(50.dp)
                        .size(200.dp)
                )
            }
        }
    }
}
