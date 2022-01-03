package cz.utb.fai.trivia.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ResultViewModel : ViewModel() {
  private val _congratulation = MutableLiveData<String>()
  val congratulation: LiveData<String>
    get() = _congratulation

  private val _score = MutableLiveData<String>()
  val score: LiveData<String>
    get() = _score

  fun showResult(userName: String,correctAnswers: Int) {
    _congratulation.value = "Hey " + userName + ", Congratulations!"
    _score.value = "Your Score is " + correctAnswers + " out of 10"
  }
}