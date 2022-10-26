package com.quizappdrink.drinkeat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.quizappdrink.drinkeat.databinding.FragmentFirstBinding
import com.quizappdrink.drinkeat.vievmodel.MainVievMooodel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class FirstFragment : Fragment() {
    var counter = 0.05f
    var diff = 0.05f
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentStartBinding = null")

    private val vievModel by activityViewModels<MainVievMooodel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        try {
            cycleUpAndDovnAlpha()

            binding.btnImgSettingsSome.setOnClickListener {
                findNavController().navigate(R.id.action_firstFragment_to_setttttingsFragment)
            }
            binding.imgBtnHelp.setOnClickListener {
                findNavController().navigate(R.id.action_firstFragment_to_helpFragment)
            }
            binding.imgDrinksQuiz.setOnClickListener {
                findNavController().navigate(R.id.action_firstFragment_to_drinkQuizFragment)
            }
            binding.imgFoodQuiz.setOnClickListener {
                findNavController().navigate(R.id.action_firstFragment_to_foodQuizFragment)
            }

        } catch (e: Exception) {
            snackBarError()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun cycleUpAndDovnAlpha() {
        lifecycleScope.launch {
            while (true) {
                binding.tvTitleChooseQuiz.alpha = counter
                if (counter >= 1f) {
                    diff = -0.05f
                }
                if (counter <= 0.1f) {
                    diff = 0.05f
                }
                delay(50)
                counter += diff
            }
        }
    }

    private fun snackBarError() {
        Snackbar.make(
            binding.root,
            "Oops something went wrong, please try again...",
            Snackbar.LENGTH_LONG
        ).show()
        requireActivity().onBackPressed()
    }

    private fun initAlertDialogForExit() {
        AlertDialog.Builder(requireContext())
            .setTitle("Exit")
            .setMessage("Current data will not be saved, are you really want to log out ?")
            .setPositiveButton("Yes, Exit") { _, _ ->
                requireActivity().onBackPressed()
            }
            .setNegativeButton("Deny") { _, _ ->
            }
            .setCancelable(true)
            .create()
            .show()
    }

}