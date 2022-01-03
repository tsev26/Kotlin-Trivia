package cz.utb.fai.trivia.questions

import android.text.Html
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.utb.fai.trivia.network.Question
import cz.utb.fai.trivia.network.TriviaApi
import kotlinx.coroutines.launch

class QuestionViewModel : ViewModel() {

  private val _response = MutableLiveData<String>()
  val response: LiveData<String>
    get() = _response

  private val _questionData = MutableLiveData<MutableList<Question>>()
  val questionData: LiveData<MutableList<Question>>
    get() = _questionData

  private val _currentQuestion = MutableLiveData<Question>()
  val currentQuestion: LiveData<Question>
    get() = _currentQuestion

  private val _currentQuestionString = MutableLiveData<String>()
  val currentQuestionString: LiveData<String>
    get() = _currentQuestionString

  private val allAnswers: MutableList<String> = ArrayList()

  private val _firstAnswer = MutableLiveData<String>()
  val firstAnswer: LiveData<String>
    get() = _firstAnswer


  private val _secondAnswer = MutableLiveData<String>()
  val secondAnswer: LiveData<String>
    get() = _secondAnswer


  private val _thirdAnswer = MutableLiveData<String>()
  val thirdAnswer: LiveData<String>
    get() = _thirdAnswer


  private val _fourthAnswer = MutableLiveData<String>()
  val fourthAnswer: LiveData<String>
    get() = _fourthAnswer


  private val _answerVisibility = MutableLiveData<Int>()
  val answerVisibility: LiveData<Int>
    get() = _answerVisibility

  private val _buttonName = MutableLiveData<String>()
  val buttonName: LiveData<String>
    get() = _buttonName

  init {
    _questionData.value = mutableListOf<Question>()
  }


  fun getQuestionProperties(id_category: Int, num: Int) {
    if (questionData.value?.size == 0)
    {
      viewModelScope.launch {
        try {
          val listResult = if (id_category == 1) {
            TriviaApi.retrofitService.getQuestions()
          } else {
            TriviaApi.retrofitService.getQuestionsByCategory(id_category)
          }
          questionData.value?.addAll(listResult.results)
          getQuestionNumber(num)
          _response.value = "Success: ${questionData.value?.size} questions retrieved"
        } catch (e: Exception) {
          _response.value = "Failure: ${e.message}"
        }
      }
    } else {
      getQuestionNumber(num)
    }
  }

  private fun getQuestionNumber(num : Int) {
    _currentQuestion.value = _questionData.value!!.get(num)
    _currentQuestionString.value = convertHtmlString(_currentQuestion.value!!.question)
    allAnswers.clear()
    allAnswers.add(currentQuestion.value!!.correctAnswer)
    allAnswers.addAll(currentQuestion.value!!.incorrectAnswer)
    allAnswers.shuffle()
    _firstAnswer.value = if (allAnswers.size > 0) convertHtmlString(allAnswers[0]) else ""
    _secondAnswer.value = if (allAnswers.size > 1) convertHtmlString(allAnswers[1]) else ""
    _thirdAnswer.value = if (allAnswers.size > 2) convertHtmlString(allAnswers[2]) else ""
    _fourthAnswer.value = if (allAnswers.size > 3) convertHtmlString(allAnswers[3]) else ""
    _answerVisibility.value = if (currentQuestion!!.value!!.type == "boolean") View.INVISIBLE else View.VISIBLE
    _buttonName.value = if (num == questionData.value!!.size) "FINISH" else "SUBMIT"
  }

  private fun convertHtmlString(htmlString: String) : String{
    return Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY).toString()
  }

}