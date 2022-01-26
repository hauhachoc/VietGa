package eu.kanade.tachiyomi.data.database.models

import eu.kanade.tachiyomi.source.model.SChapter
import eu.kanade.tachiyomi.source.model.isMergedChapter
import eu.kanade.tachiyomi.source.online.utils.MdUtil
import eu.kanade.tachiyomi.util.chapter.ChapterUtil
import java.io.Serializable

interface Chapter : SChapter, Serializable {

    var id: Long?

    var manga_id: Long?

    var read: Boolean

    var bookmark: Boolean

    var last_page_read: Int

    var pages_left: Int

    var date_fetch: Long

    var source_order: Int

    val isRecognizedNumber: Boolean
        get() = chapter_number >= 0f

    companion object {

        fun create(): Chapter = ChapterImpl().apply {
            chapter_number = -1f
        }
    }
}

fun Chapter.scanlatorList(): List<String> {
    this.scanlator ?: return emptyList()
    return ChapterUtil.getScanlators(this.scanlator!!)
}

fun Chapter.fullUrl(): String {
    return when (isMergedChapter()) {
        true -> "https://manga4life.com$url"
        false -> MdUtil.baseUrl + url
    }
}
