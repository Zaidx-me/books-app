package com.zaidxme.zesho.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zaidxme.zesho.ui.theme.DarkerGray

fun Modifier.shimmerLoadingAnimation(): Modifier = composed {
    val shimmerColors = listOf(
        DarkerGray.copy(alpha = 0.6f),
        DarkerGray.copy(alpha = 0.2f),
        DarkerGray.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer"
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    return@composed this.background(brush)
}

@Composable
fun SkeletonBookCard() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(24.dp),
        color = DarkerGray.copy(alpha = 0.2f)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Book Thumbnail Skeleton
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .background(DarkerGray.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                    .shimmerLoadingAnimation()
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                // Title Skeleton
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(20.dp)
                        .background(DarkerGray.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
                        .shimmerLoadingAnimation()
                )
                
                Spacer(modifier = Modifier.height(8.dp))

                // Author Skeleton
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .height(14.dp)
                        .background(DarkerGray.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
                        .shimmerLoadingAnimation()
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Category Chip Skeleton
                Box(
                    modifier = Modifier
                        .size(width = 60.dp, height = 20.dp)
                        .background(DarkerGray.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                        .shimmerLoadingAnimation()
                )
            }
        }
    }
}
