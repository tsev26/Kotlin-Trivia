package cz.utb.fai.trivia.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://opentdb.com"

private val moshi = Moshi.Builder()
  .add(KotlinJsonAdapterFactory())
  .build()


private val retrofit = Retrofit.Builder()
  .addConverterFactory(MoshiConverterFactory.create(moshi))
  .baseUrl(BASE_URL)
  .build()


interface TriviaApiService {
  @GET("api.php?amount=10")
  suspend fun getQuestions(): QuestionProperty

  @GET("api.php?amount=10&")
  suspend fun getQuestionsByCategory(@Query("category") id: Int): QuestionProperty

  @GET("api_category.php")
  suspend fun getCategories(): CategoryProperty
}


object TriviaApi {
  val retrofitService : TriviaApiService by lazy {
    retrofit.create(TriviaApiService::class.java) }
}