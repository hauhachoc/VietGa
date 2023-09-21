package eu.kanade.tachiyomi.network.services

import com.skydoves.sandwich.ApiResponse
import eu.kanade.tachiyomi.source.online.models.dto.MangaListDto
import eu.kanade.tachiyomi.source.online.models.dto.MarkStatusDto
import eu.kanade.tachiyomi.source.online.models.dto.RatingDto
import eu.kanade.tachiyomi.source.online.models.dto.RatingResponseDto
import eu.kanade.tachiyomi.source.online.models.dto.ReadChapterDto
import eu.kanade.tachiyomi.source.online.models.dto.ReadingStatusDto
import eu.kanade.tachiyomi.source.online.models.dto.ReadingStatusMapDto
import eu.kanade.tachiyomi.source.online.models.dto.ResultDto
import org.nekomanga.util.Constants
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MangaDexAuthorizedUserService {

    @Headers("Cache-Control: no-cache")
    @GET("${Constants.Api.userFollows}?limit=100&includes[]=${Constants.Types.coverArt}")
    suspend fun userFollowList(@Query("offset") offset: Int): ApiResponse<MangaListDto>

    @Headers("Cache-Control: no-cache")
    @GET("${Constants.Api.manga}/{id}/status")
    suspend fun readingStatusForManga(
        @Path("id") mangaId: String,
        @Header("Cache-Control") cacheControl: String = "no-cache",
    ): ApiResponse<ReadingStatusDto>

    @Headers("Cache-Control: no-cache")
    @GET("${Constants.Api.manga}/{id}/read")
    suspend fun readChaptersForManga(
        @Path("id") mangaId: String,
        @Header("Cache-Control") cacheControl: String = "no-cache",
    ): ApiResponse<ReadChapterDto>

    @POST("${Constants.Api.manga}/{id}/status")
    suspend fun updateReadingStatusForManga(
        @Path("id") mangaId: String,
        @Body readingStatusDto: ReadingStatusDto,
        @Header("Cache-Control") cacheControl: String = "no-cache",
    ): ApiResponse<ResultDto>

    @GET(Constants.Api.readingStatusForAllManga)
    suspend fun readingStatusAllManga(@Header("Cache-Control") cacheControl: String = "no-cache"): ApiResponse<ReadingStatusMapDto>

    @GET(Constants.Api.readingStatusForAllManga)
    suspend fun readingStatusByType(
        @Query("status") status: String,
        @Header("Cache-Control") cacheControl: String = "no-cache",
    ): ApiResponse<ReadingStatusMapDto>

    @POST("${Constants.Api.manga}/{id}/read")
    suspend fun markStatusForMultipleChapters(
        @Path("id") mangaId: String,
        @Body markStatusDto: MarkStatusDto,
    ): ApiResponse<ResultDto>

    @POST("${Constants.Api.manga}/{id}/follow")
    suspend fun followManga(@Path("id") mangaId: String): ApiResponse<ResultDto>

    @DELETE("${Constants.Api.manga}/{id}/follow")
    suspend fun unfollowManga(@Path("id") mangaId: String): ApiResponse<ResultDto>

    @GET(Constants.Api.rating)
    suspend fun retrieveRating(@Query("manga[]") mangaId: String): ApiResponse<RatingResponseDto>

    @POST("${Constants.Api.rating}/{id}")
    suspend fun updateRating(
        @Path("id") mangaId: String,
        @Body ratingDto: RatingDto,
    ): ApiResponse<ResultDto>

    @DELETE("${Constants.Api.rating}/{id}")
    suspend fun removeRating(@Path("id") mangaId: String): ApiResponse<ResultDto>
}
