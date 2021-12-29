package cz.utb.fai.trivia.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.utb.fai.trivia.network.TriviaApi
import kotlinx.coroutines.launch

class WelcomeViewModel : ViewModel() {

  private val _response = MutableLiveData<String>()

  // The external immutable LiveData for the response String
  val response: LiveData<String>
    get() = _response

  /**
   * Call getCategoryProperties() on init so we can display categories immediately.
   */
  init {
    getCategoryProperties()
  }

  private fun getCategoryProperties() {
    viewModelScope.launch {
      try {
        val listResult = TriviaApi.retrofitService.getCategories()
        _response.value = "Success: ${listResult.triviaCategories.size} trivia categories retrieved"
      } catch (e: Exception) {
        _response.value = "Failure: ${e.message}"
      }
    }
  }
}