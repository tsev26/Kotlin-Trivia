package cz.utb.fai.trivia.network

import com.squareup.moshi.Json

data class QuestionProperty(
  @Json(name = "response_code") val responseCode: String,
  val results: List<Question>
)

data class Question(
  val category: String,
  val type: String,
  val difficulty: String,
  val question: String,
  @Json(name = "correct_answer") val correctAnswer: String,
  @Json(name = "incorrect_answer") val incorrectAnswer: List<String>
)
