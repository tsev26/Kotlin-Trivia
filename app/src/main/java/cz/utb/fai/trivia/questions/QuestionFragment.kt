package cz.utb.fai.trivia.questions

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cz.utb.fai.trivia.R
import cz.utb.fai.trivia.databinding.FragmentQuestionBinding

class QuestionFragment : Fragment() {

  private lateinit var binding: FragmentQuestionBinding
  private val viewModel: QuestionViewModel by lazy {
    ViewModelProvider(this).get(QuestionViewModel::class.java)
  }

  private var mCurrentPosition : Int = 1
  private var mSelectedOptionPosition : Int = 0
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

    return binding.root
  }

  private fun setQuestion() {

    viewModel.getQuestionProperties(mIdCategory,mCurrentPosition - 1)

    defaultOptionsView()

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

}