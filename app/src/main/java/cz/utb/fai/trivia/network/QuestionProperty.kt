package cz.utb.fai.trivia.network

import com.squareup.moshi.Json

data class QuestionProperty(
  @Json(name = "response_code") val responseCode: String,
  val results: List<Question>
)
