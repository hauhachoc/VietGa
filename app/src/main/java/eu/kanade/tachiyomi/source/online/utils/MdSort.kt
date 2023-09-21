package eu.kanade.tachiyomi.source.online.utils

import eu.kanade.tachiyomi.ui.manga.MangaConstants
import org.nekomanga.util.Constants

enum class MdSort(val displayName: String, val key: String, val state: MangaConstants.SortState) {
    Best("Best Match", Constants.Sort.relevance, MangaConstants.SortState.Descending),
    LatestUploads("Latest uploads", Constants.Sort.latest, MangaConstants.SortState.Descending),
    OldestUploads("Oldest uploads", Constants.Sort.latest, MangaConstants.SortState.Ascending),
    TitleDescending("Title asc", Constants.Sort.title, MangaConstants.SortState.Ascending),
    TitleAscending("Title desc", Constants.Sort.title, MangaConstants.SortState.Descending),
    HighestRating("Highest rating", Constants.Sort.rating, MangaConstants.SortState.Descending),
    LowestRating("lowest rating", Constants.Sort.rating, MangaConstants.SortState.Ascending),
    MostFollows("Most follows", Constants.Sort.followCount, MangaConstants.SortState.Descending),
    LeastFollows("Fewest follows", Constants.Sort.followCount, MangaConstants.SortState.Ascending),
    RecentlyAdded("Recently added", Constants.Sort.createdAt, MangaConstants.SortState.Descending),
    OldestAdded("Oldest added", Constants.Sort.createdAt, MangaConstants.SortState.Descending),
    YearAscending("Year asc", Constants.Sort.year, MangaConstants.SortState.Ascending),
    YearDescending("Year desc", Constants.Sort.year, MangaConstants.SortState.Descending),
    // updatedAt("Information updated", Constants.Sort.updatedAt),
}
