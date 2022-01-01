package cz.utb.fai.trivia.questions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.utb.fai.trivia.network.Category
import cz.utb.fai.trivia.network.Question
import cz.utb.fai.trivia.network.QuestionProperty
import cz.utb.fai.trivia.network.TriviaApi
import kotlinx.coroutines.launch

class QuestionViewModel : ViewModel() {

  private val _response = MutableLiveData<String>()
  val response: LiveData<String>
    get() = _response

  private val _questionData = MutableLiveData<MutableList<Question>>()
  val questionData: LiveData<MutableList<Question>>
    get() = _questionData


  init {
    _questionData.value = mutableListOf<Question>()
  }

  fun getCategoryProperties(id_category: Int): LiveData<MutableList<Question>> {
    if (questionData.value?.size == 0)
    {
      viewModelScope.launch {
        try {
          val listResult = if (id_category == 0) {
            TriviaApi.retrofitService.getQuestions()
          } else {
            TriviaApi.retrofitService.getQuestionsByCategory(id_category)
          }

          questionData.value?.addAll(listResult.results)
          _response.value = "Success: ${questionData.value?.size} questions retrieved"
        } catch (e: Exception) {
          _response.value = "Failure: ${e.message}"
        }
      }
    }
    return questionData
  }
}