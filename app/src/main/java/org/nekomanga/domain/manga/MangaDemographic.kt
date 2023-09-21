package org.nekomanga.domain.manga

import eu.kanade.tachiyomi.R
import org.nekomanga.util.Constants

enum class MangaDemographic(val key: String, val nameRes: Int) {
    None(Constants.Demographic.none, R.string.none),
    Shounen(Constants.Demographic.shounen, R.string.shounen),
    Shoujo(Constants.Demographic.shoujo, R.string.shoujo),
    Seinen(Constants.Demographic.seinen, R.string.seinen),
    Josei(Constants.Demographic.josei, R.string.josei),

    ;

    companion object {
        fun getOrdered(): List<MangaDemographic> {
            return listOf(None, Shounen, Shoujo, Seinen, Josei)
        }
    }
}
