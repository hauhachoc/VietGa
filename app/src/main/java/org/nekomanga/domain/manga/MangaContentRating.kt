package org.nekomanga.domain.manga

import eu.kanade.tachiyomi.R
import eu.kanade.tachiyomi.util.lang.capitalizeWords
import org.nekomanga.util.Constants

enum class MangaContentRating(val key: String, val nameRes: Int) {
    Safe(Constants.ContentRating.safe, R.string.safe),
    Suggestive(Constants.ContentRating.suggestive, R.string.suggestive),
    Erotica(Constants.ContentRating.erotica, R.string.erotica),
    Pornographic(Constants.ContentRating.pornographic, R.string.pornographic),
    Unknown(Constants.ContentRating.unknown, R.string.unknown),
    ;

    fun prettyPrint(): String {
        return key.capitalizeWords()
    }

    companion object {
        fun getOrdered(): List<MangaContentRating> {
            return listOf(Safe, Suggestive, Erotica, Pornographic)
        }

        fun getContentRating(contentRating: String?): MangaContentRating {
            return when {
                contentRating == null -> Unknown
                contentRating.equals(Safe.key, true) -> Safe
                contentRating.equals(Suggestive.key, true) -> Suggestive
                contentRating.equals(Erotica.key, true) -> Erotica
                contentRating.equals(Pornographic.key, true) -> Pornographic
                else -> Safe
            }
        }
    }
}
