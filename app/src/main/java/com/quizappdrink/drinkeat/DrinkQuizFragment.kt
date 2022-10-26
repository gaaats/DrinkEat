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
import com.quizappdrink.drinkeat.databinding.FragmentDrinkQuizBinding
import com.quizappdrink.drinkeat.utilitas.Constances
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DrinkQuizFragment : Fragment() {
    private var _binding: FragmentDrinkQuizBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentStartBinding = null")

    private val listImagesLogoQuizFrinks = listOf(
        R.drawable.teabag,
        R.drawable.coffeecup,
        R.drawable.cocktail,
        R.drawable.energydrink,
        R.drawable.gin,
        R.drawable.drinkwater,
        R.drawable.drinker,
        R.drawable.softdrink,
    )

    private var currentQuestionDrinks = ""
    private var currentAnsDrinks = ""

    private val loadingDrinks = 1
    private val correctDrinks = 2
    private val failDrinks = 3

    private val listFakeAnsDrinks = listOf(
        "Vanilla",
        "Mint",
        "Chocolate Chip",
        "Caramel",
        "Butter Pecan",
        "Peanut Butter Cup",
        "Banana",
        "Chocolate Almond",
        "Cherry",
        "Cherry Vanilla",
        "Coffee",
        "Neopolitan",
        "Rocky Road",
        "Cookie Dough",
        "M&M's",
        "Lemon",
        "Mint Chocolate Chip",
        "Birthday Cake",
        "Chocolate Marshmallow",
        "Moose Tracks",
        "Strawberry",
        "Raspberry",
        "Pistachio",
        "Chocolate",
        "Cookies N' Cream",
        "Chocolate Chip Cookie Dough",
        "Fudge Brownie",
        "Praline Pecan",
        "French Vanilla",
        "Irish Car Bomb",
        "Negroni",
        "White Russian",
        "Electric Lemonade"
    )

    private val retrofitDrinksQuiz by lazy {
        Retrofit.Builder()
            .baseUrl(NetApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientDrinks)
            .build()
    }

    private val clientDrinks = OkHttpClient.Builder().apply {
        addInterceptor(MyInterceptor())
    }.build()

    val apiDrinks: NetApiService by lazy {
        retrofitDrinksQuiz.create(NetApiService::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDrinkQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initProgBar(loadingDrinks)

        binding.btnImgRulesDrinks.setOnClickListener {
            findNavController().navigate(R.id.action_drinkQuizFragment_to_rulesQuizzzzFragment)
        }

        try {
            binding.btnImgExitDrinks.setOnClickListener {
                initAlertDialogForExit()
            }
            binding.btnAns1Frinks.setOnClickListener {
                sendAnsFromUser(binding.btnAns1Frinks.text.toString())
            }
            binding.btnAns2Drinks.setOnClickListener {
                sendAnsFromUser(binding.btnAns2Drinks.text.toString())
            }
            binding.btnAns3Frinks.setOnClickListener {
                sendAnsFromUser(binding.btnAns3Frinks.text.toString())
            }
            binding.btnAns4Drinks.setOnClickListener {
                sendAnsFromUser(binding.btnAns4Drinks.text.toString())
            }
//            loadList()


        } catch (e: Exception) {
            snackBarError()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun sendAnsFromUser(textAns: String) {
        if (currentAnsDrinks == textAns) {
            lifecycleScope.launch {
                initProgBar(correctDrinks)
            }
        } else {
            initProgBar(failDrinks)
        }
    }

    private fun loadList() {
        lifecycleScope.launch {
            try {
                val result = apiDrinks.getQuestion()
                if (result.isSuccessful) {
                    currentQuestionDrinks = result.body()!![0].question!!
                    currentAnsDrinks = result.body()!![0].answer!!

                    binding.tvQuestionTextDrinks.text = currentQuestionDrinks
                    binding.btnAns1Frinks.text = currentAnsDrinks
                    binding.btnAns2Drinks.text = listFakeAnsDrinks.random()
                    binding.btnAns3Frinks.text = listFakeAnsDrinks.random()
                    binding.btnAns4Drinks.text = listFakeAnsDrinks.random()
                    binding.imgLogoQuestionDrinks.setImageResource(listImagesLogoQuizFrinks.random())

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
            binding.tvTitleQuizDrinks.visibility = View.GONE
            binding.btnImgExitDrinks.visibility = View.GONE
            binding.imgLogoQuestionDrinks.visibility = View.GONE
            binding.tvQuestionTextDrinks.visibility = View.GONE
            binding.tvDovnTextDrinkQuiz.visibility = View.GONE
            binding.btnAns1Frinks.visibility = View.GONE
            binding.btnAns2Drinks.visibility = View.GONE
            binding.btnAns3Frinks.visibility = View.GONE
            binding.btnAns4Drinks.visibility = View.GONE
            binding.btnImgRulesDrinks.visibility = View.GONE

            when (kindOfAnim) {
                loadingDrinks -> {
                    binding.lottieAnimLoadingQuizDrinks.visibility = View.VISIBLE
                }
                correctDrinks -> {
                    DrinkQuizFragmentDirections.actionDrinkQuizFragmentToYesFragment(quizType = Constances.TYPE_QUIZ_DRINKS)
                        .also {
                            findNavController().navigate(it)
                        }
                }
                failDrinks -> {
                    DrinkQuizFragmentDirections.actionDrinkQuizFragmentToNoFragment(quizzzzzType = Constances.TYPE_QUIZ_DRINKS)
                        .also {
                            findNavController().navigate(it)
                        }
                }
            }

            loadList()

            delay(1100)

            binding.tvTitleQuizDrinks.visibility = View.VISIBLE
            binding.btnImgExitDrinks.visibility = View.VISIBLE
            binding.imgLogoQuestionDrinks.visibility = View.VISIBLE
            binding.tvQuestionTextDrinks.visibility = View.VISIBLE
            binding.tvDovnTextDrinkQuiz.visibility = View.VISIBLE
            binding.btnAns1Frinks.visibility = View.VISIBLE
            binding.btnAns2Drinks.visibility = View.VISIBLE
            binding.btnAns3Frinks.visibility = View.VISIBLE
            binding.btnAns4Drinks.visibility = View.VISIBLE
            binding.btnImgRulesDrinks.visibility = View.VISIBLE

            binding.lottieAnimLoadingQuizDrinks.visibility = View.GONE

        }
    }
}