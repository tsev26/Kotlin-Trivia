package cz.utb.fai.trivia.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.utb.fai.trivia.network.Category
import cz.utb.fai.trivia.network.TriviaApi
import kotlinx.coroutines.launch

class WelcomeViewModel : ViewModel() {

  var categoryMap = HashMap<String, Int>()

  private val _response = MutableLiveData<String>()
  // The external immutable LiveData for the response String
  val response: LiveData<String>
    get() = _response

  private val _categoryData = MutableLiveData<MutableList<Category>>()
  val categoryData: LiveData<MutableList<Category>>
    get() = _categoryData

  private val _spinnerData = MutableLiveData<MutableList<String>>()
  val spinnerData: LiveData<MutableList<String>>
    get() = _spinnerData


  /**
   * Call getCategoryProperties() on init so we can display categories immediately.
   */
  init {
    _categoryData.value = mutableListOf<Category>()
    _spinnerData.value = mutableListOf<String>()
    getCategoryProperties()
  }

  fun getCategoryProperties() {
    viewModelScope.launch {
      try {
        val listResult = TriviaApi.retrofitService.getCategories()
        val allCategoryOption = Category(0, "All categories")

        _categoryData.value?.add(allCategoryOption)
        _categoryData.value?.addAll(listResult.triviaCategories)

        _spinnerData.value?.add("All categories")
        for (category in listResult.triviaCategories){
          _spinnerData.value?.add(category.name)
          categoryMap.put(category.name,category.id)
        }
        _response.value = "Success: ${categoryData.value?.size} trivia categories retrieved"
      } catch (e: Exception) {
        _response.value = "Failure: ${e.message}"
      }
    }
  }
}