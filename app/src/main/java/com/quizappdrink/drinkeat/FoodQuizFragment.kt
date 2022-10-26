package com.quizappdrink.drinkeat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.quizappdrink.drinkeat.api.MyInterceptor
import com.quizappdrink.drinkeat.api.NetApiService
import com.quizappdrink.drinkeat.databinding.FragmentFoodQuizBinding
import com.quizappdrink.drinkeat.utilitas.Constances
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FoodQuizFragment : Fragment() {
    private var _binding: FragmentFoodQuizBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentStartBinding = null")

    private val listImagesLogoQuizFood = listOf(
        R.drawable.baby,
        R.drawable.eating,
        R.drawable.woman,
        R.drawable.thaifood,
        R.drawable.delivery,
        R.drawable.donut,
        R.drawable.ramen,
        R.drawable.salad_1,
        R.drawable.diet_1,
        R.drawable.fastfood,
    )

    private var currentQuestionFood = ""
    private var currentAnsFood = ""

    private val loadingFood = 1
    private val correctFood = 2
    private val failFood = 3

    private val listFakeAnsFood = listOf(
        "burger",
        "flounder",
        "kidney beans",
        "heavy cream",
        "custard",
        "red chile powder",
        "pepper",
        "beans",
        "cider",
        "cannellini beans",
        "ginger",
        "monkfish",
        "huckleberries",
        "mussels",
        "breadcrumbs",
        "bouillon",
        "cauliflower",
        "rice vinegar",
        "sugar",
        "pine nuts",
        "split peas",
        "chard",
        "peanut butter",
        "black olives",
        "arugula",
        "snow peas",
        "nectarines",
        "brazil nuts",
        "pineapples",
        "hamburger",
        "Havarti cheese",
        "broccoli raab",
        "dates",
        "won ton skins",
        "plantains",
        "succotash",
        "cornmeal",
        "fennel",
        "chives",
        "baking powder",
        "octopus"
        )

    private val retrofitFoodQuiz by lazy {
        Retrofit.Builder()
            .baseUrl(NetApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    private val client = OkHttpClient.Builder().apply {
        addInterceptor(MyInterceptor())
    }.build()

    val api: NetApiService by lazy {
        retrofitFoodQuiz.create(NetApiService::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initProgBar(loadingFood)

        binding.btnImgRulesFood.setOnClickListener {
            findNavController().navigate(R.id.action_foodQuizFragment_to_rulesQuizzzzFragment)
        }

        try {
            binding.btnImgExitFood.setOnClickListener {
                initAlertDialogForExit()
            }
            binding.btnAns1Food.setOnClickListener {
                sendAnsFromUser(binding.btnAns1Food.text.toString())
            }
            binding.btnAns2Food.setOnClickListener {
                sendAnsFromUser(binding.btnAns2Food.text.toString())
            }
            binding.btnAns3Food.setOnClickListener {
                sendAnsFromUser(binding.btnAns3Food.text.toString())
            }
            binding.btnAns4Food.setOnClickListener {
                sendAnsFromUser(binding.btnAns4Food.text.toString())
            }
//            loadList()


        } catch (e: Exception) {
            snackBarError()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun sendAnsFromUser(textAns: String) {
        if (currentAnsFood == textAns) {
            lifecycleScope.launch {
                initProgBar(correctFood)
            }
        } else {
            initProgBar(failFood)
        }
    }

    private fun loadList() {
        lifecycleScope.launch {
            try {
                val result = api.getQuestion()
                if (result.isSuccessful) {
                    currentQuestionFood = result.body()!![0].question!!
                    currentAnsFood = result.body()!![0].answer!!

                    binding.tvQuestionTextFood.text = currentQuestionFood
                    binding.btnAns1Food.text = currentAnsFood
                    binding.btnAns2Food.text = listFakeAnsFood.random()
                    binding.btnAns3Food.text = listFakeAnsFood.random()
                    binding.btnAns4Food.text = listFakeAnsFood.random()
                    binding.imgLogoQuestionFood.setImageResource(listImagesLogoQuizFood.random())

                } else {
                    snackBarError()
                }
            } catch (e: Exception) {
                snackBarError()
            }
        }
    }


    private fun snackBarError() {
        Snackbar.make(
            binding.root,
            "There is some error, try again",
            Snackbar.LENGTH_LONG
        ).show()
        requireActivity().onBackPressed()
    }

    private fun initAlertDialogForExit() {
        AlertDialog.Builder(requireContext())
            .setTitle("Exit")
            .setMessage("The current data will not be saved, are you definitely want to log out?")
            .setPositiveButton("Yes, Exit") { _, _ ->
                requireActivity().onBackPressed()
            }
            .setNegativeButton("Deny") { _, _ ->
            }
            .setCancelable(true)
            .create()
            .show()
    }

    private fun initProgBar(kindOfAnim: Int) {
        lifecycleScope.launch {
            binding.tvTitleQuizFoods.visibility = View.GONE
            binding.btnImgExitFood.visibility = View.GONE
            binding.imgLogoQuestionFood.visibility = View.GONE
            binding.tvQuestionTextFood.visibility = View.GONE
            binding.tvDovnTextFoodQuiz.visibility = View.GONE
            binding.btnAns1Food.visibility = View.GONE
            binding.btnAns2Food.visibility = View.GONE
            binding.btnAns3Food.visibility = View.GONE
            binding.btnAns4Food.visibility = View.GONE
            binding.btnImgRulesFood.visibility = View.GONE

            when (kindOfAnim) {
                loadingFood -> {
                    binding.lottieAnimLoading.visibility = View.VISIBLE
                }
                correctFood -> {
                    FoodQuizFragmentDirections.actionFoodQuizFragmentToYesFragment(quizType = Constances.TYPE_QUIZ_FOOD)
                        .also {
                            findNavController().navigate(it)
                        }
                }
                failFood -> {
                    FoodQuizFragmentDirections.actionFoodQuizFragmentToNoFragment(quizzzzzType = Constances.TYPE_QUIZ_FOOD)
                        .also {
                            findNavController().navigate(it)
                        }
                }
            }

            loadList()

            delay(1100)

            binding.tvTitleQuizFoods.visibility = View.VISIBLE
            binding.btnImgExitFood.visibility = View.VISIBLE
            binding.imgLogoQuestionFood.visibility = View.VISIBLE
            binding.tvQuestionTextFood.visibility = View.VISIBLE
            binding.tvDovnTextFoodQuiz.visibility = View.VISIBLE
            binding.btnAns1Food.visibility = View.VISIBLE
            binding.btnAns2Food.visibility = View.VISIBLE
            binding.btnAns3Food.visibility = View.VISIBLE
            binding.btnAns4Food.visibility = View.VISIBLE
            binding.btnImgRulesFood.visibility = View.VISIBLE

            binding.lottieAnimLoading.visibility = View.GONE

        }
    }
}