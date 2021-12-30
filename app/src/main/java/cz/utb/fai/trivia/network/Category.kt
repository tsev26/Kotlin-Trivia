package cz.utb.fai.trivia.network

data class Category(
  val id: Int,
  val name: String) {
  override fun toString(): String {
    return name
  }
}