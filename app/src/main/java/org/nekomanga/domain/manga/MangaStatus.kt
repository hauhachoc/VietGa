package org.nekomanga.domain.manga

import androidx.annotation.StringRes
import eu.kanade.tachiyomi.R
import org.nekomanga.util.Constants

enum class MangaStatus(val status: Int, @StringRes val statusRes: Int, val key: String = "") {
    Unknown(0, R.string.unknown),
    Ongoing(1, R.string.ongoing, Constants.Status.ongoing),
    Completed(2, R.string.completed, Constants.Status.completed),
    PublicationComplete(4, R.string.publication_complete),
    Cancelled(5, R.string.cancelled, Constants.Status.cancelled),
    Hiatus(6, R.string.hiatus, Constants.Status.hiatus),
    ;

    companion object {

        fun getMangaDexStatus(): List<MangaStatus> {
            return listOf(Ongoing, Completed, Hiatus, Cancelled)
        }

        fun fromStatus(status: Int) = values().firstOrNull { it.status == status } ?: Unknown
    }
}
