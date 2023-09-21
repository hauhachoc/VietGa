package eu.kanade.tachiyomi.network.services

import com.skydoves.sandwich.ApiResponse
import eu.kanade.tachiyomi.source.online.models.dto.AggregateDto
import eu.kanade.tachiyomi.source.online.models.dto.AtHomeImageReportDto
import eu.kanade.tachiyomi.source.online.models.dto.AuthorListDto
import eu.kanade.tachiyomi.source.online.models.dto.ChapterDto
import eu.kanade.tachiyomi.source.online.models.dto.ChapterListDto
import eu.kanade.tachiyomi.source.online.models.dto.GroupListDto
import eu.kanade.tachiyomi.source.online.models.dto.LegacyIdDto
import eu.kanade.tachiyomi.source.online.models.dto.LegacyMappingDto
import eu.kanade.tachiyomi.source.online.models.dto.ListDto
import eu.kanade.tachiyomi.source.online.models.dto.MangaDto
import eu.kanade.tachiyomi.source.online.models.dto.MangaListDto
import eu.kanade.tachiyomi.source.online.models.dto.RelationListDto
import eu.kanade.tachiyomi.source.online.models.dto.RelationshipDtoList
import eu.kanade.tachiyomi.source.online.models.dto.ResultDto
import eu.kanade.tachiyomi.source.online.models.dto.StatisticResponseDto
import org.nekomanga.util.Constants
import org.nekomanga.core.network.ProxyRetrofitQueryMap
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface MangaDexService {

    @GET("${Constants.Api.manga}?includes[]=${Constants.Types.coverArt}")
    suspend fun search(@QueryMap options: ProxyRetrofitQueryMap): ApiResponse<MangaListDto>

    @GET(Constants.Api.author)
    suspend fun searchAuthor(
        @Query(value = "name") query: String,
        @Query(value = "limit") limit: Int,
    ): ApiResponse<AuthorListDto>

    @GET(Constants.Api.group)
    suspend fun searchGroup(
        @Query(value = "name") query: String,
        @Query(value = "limit") limit: Int,
    ): ApiResponse<GroupListDto>

    @GET("${Constants.Api.manga}?&order[createdAt]=desc&includes[]=${Constants.Types.coverArt}")
    suspend fun recentlyAdded(@QueryMap options: ProxyRetrofitQueryMap): ApiResponse<MangaListDto>

    @GET("${Constants.Api.manga}?&order[followedCount]=desc&includes[]=${Constants.Types.coverArt}&hasAvailableChapters=true")
    suspend fun popularNewReleases(@QueryMap options: ProxyRetrofitQueryMap): ApiResponse<MangaListDto>

    @GET("${Constants.Api.manga}/{id}?includes[]=${Constants.Types.coverArt}&includes[]=${Constants.Types.author}&includes[]=${Constants.Types.artist}")
    suspend fun viewManga(@Path("id") id: String): ApiResponse<MangaDto>

    @GET("${Constants.Api.manga}/{id}/aggregate")
    suspend fun aggregateChapters(
        @Path("id") mangaId: String,
        @Query(value = "translatedLanguage[]") translatedLanguages: List<String>,
    ): ApiResponse<AggregateDto>

    @GET("${Constants.Api.statistics}${Constants.Api.manga}/{id}")
    suspend fun mangaStatistics(
        @Path("id") mangaId: String,
    ): ApiResponse<StatisticResponseDto>

    @GET("${Constants.Api.statistics}${Constants.Api.chapter}/{id}")
    suspend fun chapterStatistics(
        @Path("id") chapterId: String,
    ): ApiResponse<StatisticResponseDto>

    @GET("${Constants.Api.manga}/{id}/relation")
    suspend fun relatedManga(@Path("id") id: String): ApiResponse<RelationListDto>

    @Headers("Cache-Control: no-cache")
    @GET("${Constants.Api.manga}/{id}/feed?limit=500&contentRating[]=${Constants.ContentRating.safe}&contentRating[]=${Constants.ContentRating.suggestive}&contentRating[]=${Constants.ContentRating.erotica}&contentRating[]=${Constants.ContentRating.pornographic}&includes[]=${Constants.Types.scanlator}&order[volume]=desc&order[chapter]=desc")
    suspend fun viewChapters(
        @Path("id") id: String,
        @Query(value = "translatedLanguage[]") translatedLanguages: List<String>,
        @Query("offset") offset: Int,
    ): ApiResponse<ChapterListDto>

    @Headers("Cache-Control: no-cache")
    @GET("${Constants.Api.chapter}?order[readableAt]=desc&includeFutureUpdates=0")
    suspend fun latestChapters(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("translatedLanguage[]") translatedLanguages: List<String>,
        @Query("contentRating[]") contentRating: List<String>,
        @Query("excludedGroups[]") blockedScanlators: List<String>,
    ): ApiResponse<ChapterListDto>

    @Headers("Cache-Control: no-cache")
    @GET("${Constants.Api.cover}?order[volume]=desc")
    suspend fun viewArtwork(
        @Query("manga[]") mangaUUID: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): ApiResponse<RelationshipDtoList>

    @Headers("Cache-Control: no-cache")
    @GET("${Constants.Api.chapter}/{id}")
    suspend fun viewChapter(@Path("id") id: String): ApiResponse<ChapterDto>

    @Headers("Cache-Control: no-cache")
    @GET("${Constants.Api.manga}/random")
    suspend fun randomManga(@Query("contentRating[]") contentRating: List<String>): ApiResponse<MangaDto>

    @Headers("Cache-Control: no-cache")
    @GET(Constants.Api.group)
    suspend fun scanlatorGroup(@Query("name") scanlator: String): ApiResponse<GroupListDto>

    @GET("${Constants.Api.list}/{id}")
    suspend fun viewList(@Path("id") id: String): ApiResponse<ListDto>

    @POST(Constants.Api.legacyMapping)
    suspend fun legacyMapping(@Body legacyMapping: LegacyIdDto): ApiResponse<LegacyMappingDto>

    @POST(Constants.atHomeReportUrl)
    suspend fun atHomeImageReport(@Body atHomeImageReportDto: AtHomeImageReportDto): ApiResponse<ResultDto>
}
