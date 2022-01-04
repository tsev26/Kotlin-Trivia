package cz.utb.fai.trivia.questions

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import cz.utb.fai.trivia.R
import cz.utb.fai.trivia.databinding.FragmentQuestionBinding

class QuestionFragment : Fragment(), View.OnClickListener {

  private lateinit var binding: FragmentQuestionBinding
  private val viewModel: QuestionViewModel by lazy {
    ViewModelProvider(this).get(QuestionViewModel::class.java)
  }

  private var mCurrentPosition : Int = 1
  private var mSelectedOption : String? = null
  private var mCorrectAnswers : Int = 0
  private var mUserName : String? = null
  private var mIdCategory : Int = 0

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    arguments = getArguments()
    mUserName = arguments?.getString("USER")
    mIdCategory = arguments?.getInt("ID_CATEGORY")!!

    binding = FragmentQuestionBinding.inflate(inflater)
    binding.lifecycleOwner = this
    binding.viewModel = viewModel

    setQuestion()

    binding.tvOptionOne.setOnClickListener(this)
    binding.tvOptionTwo.setOnClickListener(this)
    binding.tvOptionThree.setOnClickListener(this)
    binding.tvOptionFour.setOnClickListener(this)

    binding.btnSubmit.setOnClickListener(this)

    return binding.root
  }

  private fun setQuestion() {

    viewModel.getQuestionProperties(mIdCategory,mCurrentPosition - 1)

    defaultOptionsView()

    binding.progressBar.progress = mCurrentPosition
    binding.tvProgress.text = "$mCurrentPosition" + "/" + binding.progressBar.max
  }



  override fun onClick(v: View?) {
    when(v?.id){
      R.id.tv_option_one -> {selectedOptionView(binding.tvOptionOne)}
      R.id.tv_option_two -> {selectedOptionView(binding.tvOptionTwo)}
      R.id.tv_option_three -> {selectedOptionView(binding.tvOptionThree)}
      R.id.tv_option_four -> {selectedOptionView(binding.tvOptionFour)}

      R.id.btn_submit -> {
        if(mSelectedOption == null){
          mCurrentPosition++
          when {
            mCurrentPosition <= viewModel.questionData.value!!.size -> { setQuestion() }
            else -> {

              val bundle = Bundle()
              bundle.putString("USER", mUserName)
              bundle.putInt("CORRECT_ANSWERS", mCorrectAnswers)

              v.findNavController().navigate(R.id.action_questionFragment_to_resultFragment, bundle)
              //Toast.makeText(requireContext(), "You have successfully completed the Quiz", Toast.LENGTH_SHORT).show()
            }
          }
        }else{
          val question = viewModel.currentQuestion
          if(question.value!!.correctAnswer != mSelectedOption){
            answerView(mSelectedOption!!, R.drawable.wrong_option_border_bg)
          }else{
            mCorrectAnswers++
          }
          answerView(question.value!!.correctAnswer, R.drawable.correct_option_border_bg)

          if(mCurrentPosition == viewModel.questionData.value!!.size){
            binding.btnSubmit.text = "FINISH"
          }else{
            binding.btnSubmit.text = "GO TO NEXT QUESTION"
          }
          mSelectedOption = null
        }
      }
    }
  }

  private fun defaultOptionsView(){
    val options = ArrayList<TextView>()
    options.add(0, binding.tvOptionOne)
    options.add(1, binding.tvOptionTwo)
    options.add(2, binding.tvOptionThree)
    options.add(3, binding.tvOptionFour)

    for (option in options) {
      option.setTextColor(Color.parseColor("#7A8089"))
      option.typeface = Typeface.DEFAULT
      option.background = ContextCompat.getDrawable(requireContext(), R.drawable.default_option_border_bg)
    }
  }

  private fun selectedOptionView(tv: TextView){
    defaultOptionsView()
    mSelectedOption = tv.text.toString()

    tv.setTextColor(Color.parseColor("#363A43"))
    tv.setTypeface(tv.typeface, Typeface.BOLD)
    tv.background = ContextCompat.getDrawable(requireContext(), R.drawable.selected_option_border_bg)
  }

  private fun answerView(answer: String, drawableView: Int){
    when(Html.fromHtml(answer, Html.FROM_HTML_MODE_LEGACY).toString()){
      binding.tvOptionOne.text -> {binding.tvOptionOne.background = ContextCompat.getDrawable(requireContext(), drawableView)}
      binding.tvOptionTwo.text -> {binding.tvOptionTwo.background = ContextCompat.getDrawable(requireContext(), drawableView)}
      binding.tvOptionThree.text -> {binding.tvOptionThree.background = ContextCompat.getDrawable(requireContext(), drawableView)}
      binding.tvOptionFour.text -> {binding.tvOptionFour.background = ContextCompat.getDrawable(requireContext(), drawableView)}
    }
  }

}