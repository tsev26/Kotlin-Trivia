package cz.utb.fai.trivia.network

class CategoryData {
  var cateforyList: List<Category> = listOf(
    Category(1,"All Categories"),
    Category(9,"General Knowledge"),
    Category(10,"Entertainment: Books"),
    Category(11,"Entertainment: Film"),
    Category(12,"Entertainment: Music"),
    Category(13,"Entertainment: Musicals & Theatres"),
    Category(14,"Entertainment: Television"),
    Category(15,"Entertainment: Video Games"),
    Category(16,"Entertainment: Board Games"),
    Category(17,"Science & Nature"),
    Category(18,"Science: Computers"),
    Category(19,"Science: Mathematics"),
    Category(20,"Mythology"),
    Category(21,"Sports"),
    Category(22,"Geography"),
    Category(23,"History"),
    Category(24,"Politics"),
    Category(25,"Art"),
    Category(26,"Celebrities"),
    Category(27,"Animals"),
    Category(28,"Vehicles"),
    Category(29,"Entertainment: Comics"),
    Category(30,"Science: Gadgets"),
    Category(31,"Entertainment: Japanese Anime & Manga"),
    Category(32,"Entertainment: Cartoon & Animations"));

  fun findCategory(name: String): Int? {
    for (cat in cateforyList) {
      if (cat.name == name) {
        return cat.id
      }
    }
    return null
  }
}