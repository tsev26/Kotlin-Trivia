package cz.utb.fai.trivia.network

import com.squareup.moshi.Json

data class CategoryProperty(
  @Json(name = "trivia_categories") val triviaCategories: List<Category>
)


