package org.nekomanga.presentation.screens.mangadetails

import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.HotelClass
import androidx.compose.material.icons.outlined._18UpRating
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crazylegend.string.isNotNullOrEmpty
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.mikepenz.iconics.typeface.library.community.material.CommunityMaterial
import com.skydoves.balloon.compose.Balloon
import com.skydoves.balloon.compose.BalloonWindow
import eu.kanade.tachiyomi.R
import eu.kanade.tachiyomi.source.model.SManga
import eu.kanade.tachiyomi.source.online.utils.MdLang
import java.text.NumberFormat
import java.util.Locale
import jp.wasabeef.gap.Gap
import kotlin.math.roundToInt
import org.nekomanga.domain.manga.Stats
import org.nekomanga.presentation.components.VietGaColors
import org.nekomanga.presentation.components.NoRippleText
import org.nekomanga.presentation.extensions.surfaceColorAtElevationCustomColor
import org.nekomanga.presentation.screens.ThemeColorState
import toolTipBuilder

@Composable
fun InformationBlock(
    themeColorState: ThemeColorState,
    titleProvider: () -> String,
    authorProvider: () -> String,
    artistProvider: () -> String,
    statsProvider: () -> Stats?,
    langFlagProvider: () -> String?,
    statusProvider: () -> Int,
    isPornographicProvider: () -> Boolean,
    missingChaptersProvider: () -> String?,
    estimatedMissingChapterProvider: () -> String?,
    modifier: Modifier = Modifier,
    isExpandedProvider: () -> Boolean,
    showMergedIconProvider: () -> Boolean,
    titleLongClick: (String) -> Unit = {},
    creatorCopyClick: (String) -> Unit = {},
    creatorSearchClick: (String) -> Unit = {},
) {
    val highAlpha = MaterialTheme.colorScheme.onSurface.copy(alpha = VietGaColors.highAlphaLowContrast)
    val mediumAlpha = MaterialTheme.colorScheme.onSurface.copy(alpha = VietGaColors.mediumAlphaLowContrast)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 8.dp),
    ) {
        if (titleProvider().isNotNullOrEmpty()) {
            NoRippleText(
                text = titleProvider(),
                maxLines = if (isExpandedProvider()) Integer.MAX_VALUE else 4,
                onLongClick = titleLongClick,
                style = MaterialTheme.typography.headlineSmall.copy(letterSpacing = (-.5).sp, fontWeight = FontWeight.Medium),
                color = highAlpha,
            )
        }

        if (authorProvider().isNotEmpty() || artistProvider().isNotEmpty()) {
            val creator =
                when (authorProvider() == artistProvider()) {
                    true -> authorProvider().trim()
                    false -> {
                        listOfNotNull(authorProvider().trim(), artistProvider().trim())
                            .joinToString(" • ")
                    }
                }

            Gap(4.dp)

            var balloonWindow: BalloonWindow? by remember { mutableStateOf(null) }

            Balloon(
                builder = toolTipBuilder(backgroundColor = MaterialTheme.colorScheme.surfaceColorAtElevationCustomColor(themeColorState.buttonColor, 16.dp), dismissable = false, wrapHeight = true),
                balloonContent = {
                    val creators = creator.split(" • ").map { it.trim() }
                    val result = when (creators.size > 5) {
                        true -> listOf(creator)
                        false -> creators
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        result.forEach {
                            Text(text = it, style = MaterialTheme.typography.labelLarge)
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround) {
                                TextButton(
                                    onClick = {
                                        balloonWindow?.dismiss()
                                        creatorCopyClick(it)
                                    },
                                ) {
                                    Text(text = stringResource(id = R.string.copy), color = themeColorState.buttonColor)
                                }
                                TextButton(
                                    onClick = {
                                        balloonWindow?.dismiss()
                                        creatorSearchClick(it)
                                    },
                                ) {
                                    Text(text = stringResource(id = R.string.search), color = themeColorState.buttonColor)
                                }
                            }
                            Gap(4.dp)
                        }
                    }
                },

                ) { window ->

                LaunchedEffect(Unit) {
                    balloonWindow = window
                }

                NoRippleText(
                    text = creator,
                    onClick = {
                        window.showAsDropDown()
                    },
                    maxLines = if (isExpandedProvider()) 5 else 2,
                    style = MaterialTheme.typography.bodyLarge,
                    color = mediumAlpha,
                )
            }
        }

        if (statusProvider() != 0) {
            Gap(4.dp)
            val statusRes = when (statusProvider()) {
                SManga.ONGOING -> R.string.ongoing
                SManga.COMPLETED -> R.string.completed
                SManga.LICENSED -> R.string.licensed
                SManga.PUBLICATION_COMPLETE -> R.string.publication_complete
                SManga.HIATUS -> R.string.hiatus
                SManga.CANCELLED -> R.string.cancelled
                else -> R.string.unknown
            }

            NoRippleText(
                text = stringResource(id = statusRes),
                style = MaterialTheme.typography.bodyLarge,
                color = mediumAlpha,
            )
        }
        if (statsProvider() != null || langFlagProvider() != null) {
            Gap(8.dp)
        }
        FlowRow(
            modifier = Modifier
                .fillMaxWidth(),
            mainAxisAlignment = FlowMainAxisAlignment.Start,
            crossAxisAlignment = FlowCrossAxisAlignment.Center,

            ) {
            if (langFlagProvider() != null) {
                val flag = MdLang.fromIsoCode(langFlagProvider()!!.lowercase(Locale.US))?.iconResId
                if (flag != null) {
                    val drawable = AppCompatResources.getDrawable(LocalContext.current, flag)
                    Image(
                        painter = rememberDrawablePainter(drawable = drawable),
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp)),
                        contentDescription = "flag",
                    )
                }
            }

            if (isPornographicProvider()) {
                Row {
                    Gap(8.dp)
                    Image(imageVector = Icons.Outlined._18UpRating, modifier = Modifier.size(32.dp), contentDescription = null, colorFilter = ColorFilter.tint(Color.Red))
                }
            }

            statsProvider()?.let { stats ->
                stats.rating?.let { rating ->
                    val formattedRating = ((rating.toDouble() * 100).roundToInt() / 100.0).toString()

                    Row {
                        Gap(8.dp)
                        Image(imageVector = Icons.Filled.HotelClass, contentDescription = null, colorFilter = ColorFilter.tint(mediumAlpha))
                        Gap(4.dp)
                        NoRippleText(
                            text = formattedRating,
                            style = MaterialTheme.typography.bodyLarge,
                            color = mediumAlpha,
                        )
                    }
                }

                stats.follows?.let { unformattedNumberOfUsers ->
                    val numberOfUsers = runCatching {
                        NumberFormat.getNumberInstance(Locale.US).format(unformattedNumberOfUsers.toInt())
                    }.getOrDefault(0).toString()

                    Row {
                        Gap(8.dp)
                        Image(imageVector = Icons.Filled.Bookmarks, contentDescription = null, colorFilter = ColorFilter.tint(mediumAlpha))
                        Gap(4.dp)
                        NoRippleText(
                            text = numberOfUsers,
                            style = MaterialTheme.typography.bodyLarge,
                            color = mediumAlpha,
                        )
                    }
                }

                if (stats.threadId != null) {

                    val numberOfComments = runCatching {
                        NumberFormat.getNumberInstance(Locale.US).format(stats.repliesCount?.toInt())
                    }.getOrDefault(0).toString()

                    Row {
                        Gap(8.dp)
                        Image(imageVector = Icons.Filled.Comment, contentDescription = null, colorFilter = ColorFilter.tint(mediumAlpha))
                        Gap(4.dp)
                        NoRippleText(
                            text = numberOfComments,
                            style = MaterialTheme.typography.bodyLarge,
                            color = mediumAlpha,
                        )
                    }
                }

            }



            if (showMergedIconProvider()) {
                Row {
                    Gap(8.dp)
                    com.mikepenz.iconics.compose.Image(asset = CommunityMaterial.Icon.cmd_check_decagram, colorFilter = ColorFilter.tint(mediumAlpha))
                }
            }
        }

        var showEstimatedMissingChapters by remember { mutableStateOf(false) }

        missingChaptersProvider()?.let { numberOfMissingChapters ->
            Gap(4.dp)
            NoRippleText(
                text = stringResource(id = R.string.missing_chapters, numberOfMissingChapters),
                style = MaterialTheme.typography.bodyLarge,
                color = mediumAlpha,
                onClick = { showEstimatedMissingChapters = !showEstimatedMissingChapters },
            )
        }

        AnimatedVisibility(visible = showEstimatedMissingChapters) {
            estimatedMissingChapterProvider()?.let { estimates ->
                Column {
                    Gap(4.dp)
                    NoRippleText(
                        text = estimates,
                        maxLines = 4,
                        style = MaterialTheme.typography.bodySmall,
                        color = mediumAlpha,
                    )
                }

            }
        }

    }
}
