package cz.utb.fai.trivia.network

import android.os.Build
import android.text.Html
import com.squareup.moshi.Json

data class Question(
  val category: String,
  val type: String,
  val difficulty: String,
  val question: String,
  @Json(name = "correct_answer") val correctAnswer: String,
  @Json(name = "incorrect_answers") val incorrectAnswer: List<String>
)

